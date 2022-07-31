package com.petrotec.documentms.services.product;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PageResponse;
import com.petrotec.api.queue.BaseMessage;
import com.petrotec.queue.annotations.MqttSubscribe;
import com.petrotec.service.annotations.PetroScheduled;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.documentms.clients.ProductClient;
import com.petrotec.documentms.dtos.SiteProductDTO;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteProduct;
import com.petrotec.documentms.entities.product.Product;
import com.petrotec.documentms.mappers.SiteProductMapper;
import com.petrotec.documentms.repositories.SiteProductRepository;
import com.petrotec.documentms.services.SiteService;
import io.micronaut.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@Transactional
public class SiteProductService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteProductService.class);

    private final SiteProductRepository siteProductRepository;
    private final SiteService siteService;
    private final ProductService productService;
    private final SiteProductMapper siteProductMapper;

    private final ProductClient productClient;

    public SiteProductService(SiteProductRepository siteProductRepository, SiteService siteService, ProductService productService, SiteProductMapper siteProductMapper,
                              ProductClient productClient) {
        this.siteProductRepository = siteProductRepository;
        this.siteService = siteService;
        this.productService = productService;
        this.siteProductMapper = siteProductMapper;
        this.productClient = productClient;
    }

    @PetroScheduled(initialDelay = "40s", fixedDelay = "12h", backOff = "3")
    public boolean syncData() {
        LOG.debug("Sync SiteProduct Call");
        int pageSize = 30;
        int offset = 0;

        while (true) {
            BaseResponse<PageResponse<SiteProductDTO>> remoteDataResponse = productClient.getSiteProductList(offset, pageSize, true, null);
            if (remoteDataResponse.getCode() == HttpStatus.OK.getCode() && remoteDataResponse.getResult() != null) {
                /* update and create the remote items */
                remoteDataResponse.getResult().getItems().forEach(rsp -> this.createSiteProduct(rsp, null, null));

                if (remoteDataResponse.getResult().isLast())
                    return true;
            } else
                return false;
            offset = offset + pageSize;
        }

        /*TODO remove if local is not contained in remote items */
    }

    public SiteProduct createSiteProduct(SiteProductDTO dto, String rankOrder, String locale) {
        Site site = siteService.getSiteByCode(dto.getSiteCode());
        Product product = productService.getByCode(dto.getProductCode(), rankOrder, locale);
        SiteProduct siteProduct = siteProductRepository.findByProductCodeAndSiteCode(dto.getProductCode(), dto.getSiteCode()).orElseGet(() -> {
            SiteProduct sp = new SiteProduct();
            sp.setSite(site);
            sp.setProduct(product);
            return sp;
        });
        siteProduct.setFccProductCode(dto.getFccProductCode());
        SiteProduct savedSiteProduct = siteProductRepository.save(siteProduct);
        return savedSiteProduct;
    }

    public SiteProductDTO removeSiteProduct(String siteCode, String productCode) {
        SiteProduct siteProduct = siteProductRepository.findByProductCodeAndSiteCode(productCode, siteCode)
                .orElseThrow(() -> new EntityNotFoundException("site.site_product.not_found", "siteProduct not found"));
        siteProductRepository.delete(siteProduct);
        return siteProductMapper.toDTO(siteProduct);
    }


    @MqttSubscribe(topicName = "pcs/product-ms/siteProduct")
    public void handleReceivedMessage(BaseMessage<SiteProductDTO> baseMessage) {
        switch (baseMessage.getOperation()) {
            case CREATED:
            case UPDATED:
                this.createSiteProduct(baseMessage.getResult(), null, null);
                break;
            case DELETED:
                try {
                    this.removeSiteProduct(baseMessage.getResult().getSiteCode(), baseMessage.getResult().getProductCode());
                } catch (EntityNotFoundException e) {
                    LOG.info(e.getMessage());
                }

                break;
        }
    }

}
