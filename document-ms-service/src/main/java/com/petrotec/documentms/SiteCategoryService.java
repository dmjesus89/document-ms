package com.petrotec.documentms;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageResponse;
import com.petrotec.api.Sort;
import com.petrotec.api.dtos.categories.CategoryDTO;
import com.petrotec.api.queue.BaseMessage;
import com.petrotec.categories.domain.categories.Category;
import com.petrotec.categories.mappers.categories.CategoryElementMapper;
import com.petrotec.categories.mappers.categories.CategoryMapper;
import com.petrotec.categories.mappers.categories.CategoryUsageMapper;
import com.petrotec.categories.repositories.categories.CategoryElementRepository;
import com.petrotec.categories.repositories.categories.CategoryRepository;
import com.petrotec.categories.repositories.categories.CategoryUsageRepository;
import com.petrotec.categories.services.CategoryService;
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
public class SiteCategoryService extends CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteCategoryService.class);

    private final PartyClient partyClient;

    public SiteCategoryService(CategoryRepository categoryRepository, CategoryUsageRepository categoryUsageRepository,
                               CategoryElementRepository categoryElementRepository, CategoryMapper categoryMapper, CategoryUsageMapper categoryUsageMapper, CategoryElementMapper categoryElementMapper,
                               PartyClient partyClient) {
        super(categoryRepository, categoryUsageRepository, categoryElementRepository, categoryMapper, categoryUsageMapper, categoryElementMapper);
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

        List<String> existingCodes = categoryRepository.findAll().stream().map(Category::getCode).collect(Collectors.toList());

        while (true) {
            BaseResponse<PageResponse<CategoryDTO>> remoteDataResponse = partyClient.getCategories(null, currentOffset, pageSize, null, null);
            if (remoteDataResponse.getCode() == HttpStatus.OK.getCode() && remoteDataResponse.getResult() != null) {

                for (CategoryDTO category : remoteDataResponse.getResult().getItems()) {
                    existingCodes.remove(category.getCode());
                    if (!categoryRepository.findByCode(category.getCode()).isPresent()) {
                        this.createDTO(category, null, null);
                    } else {
                        this.updateDTO(category.getCode(), category, null, null);
                    }
                }
            } else {
                LOG.error("Error processing category data.");
                return false;
            }

            if (remoteDataResponse.getResult().isLast()) {
                existingCodes.forEach(code -> LOG.error("Deleting category with code {}", code));
                categoryRepository.deleteByCodeIn(existingCodes);
                return true;
            }

            currentOffset += pageSize;

        }
    }

    @Override
    @MqttSubscribe(topicName = "pcs/party-ms/categories")
    public synchronized void handleReceivedMessage(BaseMessage<CategoryDTO> baseMessage) {
        super.handleReceivedMessage(baseMessage);
    }

}
