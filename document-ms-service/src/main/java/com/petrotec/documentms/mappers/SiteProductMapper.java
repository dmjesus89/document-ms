package com.petrotec.documentms.mappers;


import com.petrotec.documentms.dtos.SiteProductDTO;
import com.petrotec.documentms.entities.SiteProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jsr330")
public abstract class SiteProductMapper {

    @Mapping(source = "product.code", target = "productCode")
    @Mapping(source = "site.code", target = "siteCode")
    public abstract SiteProductDTO toDTO(SiteProduct siteProduct);

}
