package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
@Schema(description = "Customer identification data")
public class DocumentPartyDTO {
    @Schema(description = "customer vatin", example = "212222222")
    private String vatin;

    @Deprecated
    @Schema(description = "customer used salutation", example = "MR")
    private String salutation;

    @Schema(description = "Customer name", example = "name")
    private String name;

    @Schema(description = "Customer Street address", example = "address")
    private String street;

    @Schema(description = "Customer Postal Zip Code", example = "2000-000")
    private String postalZipCode;

    @Schema(description = "Customer city", example = "Lisbon")
    private String city;

    @Schema(description = "Customer country code", example = "PT")
    private String isoCountryCode;

    public String getVatin() {
        return vatin;
    }

    public void setVatin(String vatin) {
        this.vatin = vatin;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalZipCode() {
        return postalZipCode;
    }

    public void setPostalZipCode(String postalZipCode) {
        this.postalZipCode = postalZipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsoCountryCode() {
        return isoCountryCode;
    }

    public void setIsoCountryCode(String isoCountryCode) {
        this.isoCountryCode = isoCountryCode;
    }

}
