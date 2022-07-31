package com.petrotec.documentms;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageResponse;
import com.petrotec.api.Sort;
import com.petrotec.api.dtos.properties.PropertyDTO;
import com.petrotec.api.queue.BaseMessage;
import com.petrotec.categories.domain.properties.Property;
import com.petrotec.categories.mappers.properties.PropertyMapper;
import com.petrotec.categories.repositories.properties.PropertyRepository;
import com.petrotec.categories.services.PropertyService;
import com.petrotec.queue.annotations.MqttSubscribe;
import com.petrotec.service.annotations.PetroScheduled;
import com.petrotec.documentms.clients.PartyClient;
import io.micronaut.context.annotation.Primary;
import io.micronaut.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Transactional
@Primary
public class SitePropertyService extends PropertyService {

    private static final Logger LOG = LoggerFactory.getLogger(SitePropertyService.class);

    private final PartyClient partyClient;

    public SitePropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper,
                               PartyClient partyClient) {
        super(propertyRepository, propertyMapper);
        this.partyClient = partyClient;
    }

    @PetroScheduled(initialDelay = "10s", fixedDelay = "12h", backOff = "3")
    public boolean syncData() {
        List<Sort> sortList = new ArrayList<>();
        Sort sort = new Sort();
        sort.setField("code");
        sort.setOrder(Sort.Order.DESC);
        sortList.add(sort);

        int currentOffset = 0;
        int pageSize = 10;

        List<String> existingCodes = propertyRepository.findAll().stream().map(Property::getCode).collect(Collectors.toList());

        while (true) {
            BaseResponse<PageResponse<PropertyDTO>> remoteDataResponse = partyClient.getProperties(null, currentOffset, pageSize, null, null);
            if (remoteDataResponse.getCode() == HttpStatus.OK.getCode() && remoteDataResponse.getResult() != null) {

                for (PropertyDTO prop : remoteDataResponse.getResult().getItems()) {
                    existingCodes.remove(prop.getCode());
                    if (!propertyRepository.findByCode(prop.getCode()).isPresent()) {
                        this.createDTO(prop, null, null);
                    } else {
                        this.updateDTO(prop.getCode(), prop, null, null);
                    }
                }

            } else {
                LOG.error("Error processing property data.");
                return false;
            }

            if (remoteDataResponse.getResult().isLast()) {
                existingCodes.forEach(code -> LOG.error("Deleting property with code {}", code));
                propertyRepository.deleteByCodeIn(existingCodes);
                return true;
            }


            currentOffset += pageSize;

        }
    }

    @Override
    @MqttSubscribe(topicName = "pcs/party-ms/properties")
    public synchronized void handleReceivedMessage(BaseMessage<PropertyDTO> baseMessage) {
        super.handleReceivedMessage(baseMessage);
    }
}
