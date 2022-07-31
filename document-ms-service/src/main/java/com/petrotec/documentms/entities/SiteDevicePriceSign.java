package com.petrotec.documentms.entities;

import com.petrotec.service.converters.JpaConverterJsonMapObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "site_device_price_sign")
@NoArgsConstructor
@AllArgsConstructor
public class SiteDevicePriceSign extends SiteDevice implements CommunicationDevice {

    @Column(name = "price_sign_code")
    protected String priceSignCode;

    @ManyToOne
    @JoinColumn(name = "site_device_fcc_id", referencedColumnName = "id")
    private SiteDeviceFCC siteDeviceFCC;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "communication_method_id", referencedColumnName = "id", nullable = false)
    private CommunicationMethod communicationMethod;

    @Column(name = "communication_method_data", columnDefinition = "json")
    @Convert(converter = JpaConverterJsonMapObject.class)
    private Map<String, Object> communicationMethodData;

    @OneToMany(mappedBy = "siteDevicePriceSign", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<GradePriceSign> grades = new ArrayList<>();

    public SiteDeviceFCC getSiteDeviceFCC() {
        return siteDeviceFCC;
    }

    public void setSiteDeviceFCC(SiteDeviceFCC siteDeviceFCC) {
        this.siteDeviceFCC = siteDeviceFCC;
    }

    public String getPriceSignCode() {
        return priceSignCode;
    }

    public void setPriceSignCode(String priceSignCode) {
        this.priceSignCode = priceSignCode;
    }

    public List<GradePriceSign> getGrades() {
        return grades;
    }

    public void setGrades(List<GradePriceSign> grades) {
        this.grades = grades;
    }

    @Override
    public CommunicationMethod getCommunicationMethod() {
        return communicationMethod;
    }

    @Override
    public void setCommunicationMethod(CommunicationMethod communicationMethod) {
        this.communicationMethod = communicationMethod;
    }

    @Override
    public Map<String, Object> getCommunicationMethodData() {
        return communicationMethodData;
    }

    @Override
    public void setCommunicationMethodData(Map<String, Object> communicationMethodData) {
        this.communicationMethodData = communicationMethodData;
    }
}
