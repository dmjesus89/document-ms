package com.petrotec.documentms.services.documents;


import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageResponse;
import com.petrotec.api.queue.BaseMessage;
import com.petrotec.queue.annotations.MqttSubscribe;
import com.petrotec.service.annotations.PetroScheduled;
import com.petrotec.documentms.clients.PartyClient;
import com.petrotec.documentms.dtos.documents.PromptDTO;
import com.petrotec.documentms.entities.documents.Prompt;
import com.petrotec.documentms.mappers.documents.PromptMapper;
import com.petrotec.documentms.repositories.documents.PromptRepository;
import io.micronaut.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
@Transactional
public class PromptService {
    private static final Logger LOG = LoggerFactory.getLogger(PromptService.class);

    private final PromptRepository promptRepository;
    private final PromptMapper promptMapper;
    private final PartyClient partyClient;

    public PromptService(PromptRepository promptRepository, PromptMapper promptMapper, PartyClient partyClient) {
        this.promptRepository = promptRepository;
        this.promptMapper = promptMapper;
        this.partyClient = partyClient;
    }

    @PetroScheduled(initialDelay = "60s", fixedDelay = "12h", backOff = "3")
    public boolean syncData() {
        /*TODO For each tenant call this? */
        BaseResponse<PageResponse<PromptDTO>> remoteDataResponse = partyClient.getPromptList(null, null, null, null, null);

        /*TODO Divide this you wanna have a method with param if want to manually import data instead of grabbing data from remote api*/
        if (remoteDataResponse.getCode() == HttpStatus.OK.getCode() && remoteDataResponse.getResult() != null) {
            Set<String> localDataCodes = promptRepository.findAll().stream().map(Prompt::getCode).collect(Collectors.toSet());
            /*Update all existing PRODUCT GROUPS that exists in syncRequest (apenas update de remote updatedOn > local updatedOn)*/
            remoteDataResponse.getResult().getItems().iterator().forEachRemaining(remoteItem -> {
                if (localDataCodes.contains(remoteItem.getCode())) {
                    this.updateDTO(remoteItem.getCode(), remoteItem, null, null); /* TODO question how do microservice talk to other microservice without tenant id? */
                } else if (!localDataCodes.contains(remoteItem.getCode())) {
                    this.createDTO(remoteItem, null, null); /* TODO question how do microservice talk to other microservice without tenant id? */
                }
            });
            return true;
        }
        return false;
    }

    public Prompt getByCode(@NotEmpty String code, String rankOrder) {
        return promptRepository.findByCode(code).orElseThrow(() -> {
            LOG.debug("No Prompt found for code.");
            return new EntityNotFoundException("No Prompt found for code.");
        });
    }

    public Prompt create(Prompt entity) {
        LOG.debug("Create Prompt");
        Prompt savedEntity = promptRepository.save(entity);
        promptRepository.getEntityManager().flush();
        promptRepository.getEntityManager().refresh(savedEntity);
        LOG.debug("Successfully created Prompt to {}", savedEntity);
        return savedEntity;
    }

    public Prompt update(Prompt entity) {
        LOG.debug("Update Prompt");
        Prompt savedEntity = promptRepository.update(entity);
        LOG.debug("Successfully updated Prompt to {}", savedEntity);
        return savedEntity;
    }

    public PromptDTO createDTO(PromptDTO dto, String rankOrder, String locale) {
        return promptMapper.toDTO(this.create(promptMapper.fromDTO(dto)));
    }

    public PromptDTO updateDTO(@NotBlank String code, PromptDTO dto, String locale, String rankOrder) {
        Prompt entity = getByCode(code, rankOrder);
        promptMapper.updateEntity(entity, dto);
        Prompt result = update(entity);
        return promptMapper.toDTO(result);
    }

    @MqttSubscribe(topicName = "pcs/party-ms/prompts")
    public void handleReceivedMessage(BaseMessage<PromptDTO> baseMessage) {
        switch (baseMessage.getOperation()) {
            case CREATED:
            case UPDATED:
                if (promptRepository.existsByCode(baseMessage.getResult().getCode())) {
                    this.updateDTO(baseMessage.getResult().getCode(), baseMessage.getResult(), null, null);
                } else {
                    this.createDTO(baseMessage.getResult(), null, null);
                }
                break;

            case DELETED:
                LOG.error("WHAT ARE YOU SYNCING ABOUT");
                break;
        }
    }

}
