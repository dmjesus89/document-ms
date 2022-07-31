package com.petrotec.documentms.entities;

import com.petrotec.service.converters.JpaConverterJson;
import com.petrotec.documentms.common.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "grade")
public class Grade extends BaseEntity {

    private String code;

    @Column(name = "description", columnDefinition = "json")
    @Convert(converter = JpaConverterJson.class)
    private Map<String, String> description;

    private String color;

    @Column(name = "unit_price_net")
    private BigDecimal unitPriceNet;

    @Column(name = "is_enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GradeProduct> gradeProducts = new HashSet<>();

    public Grade() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getUnitPriceNet() {
        return unitPriceNet;
    }

    public void setUnitPriceNet(BigDecimal unitPriceNet) {
        this.unitPriceNet = unitPriceNet;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<GradeProduct> getGradeProducts() {
        return gradeProducts;
    }

    public void setGradeProducts(Set<GradeProduct> gradeProducts) {
        this.gradeProducts = gradeProducts;
    }

    public GradeProduct getMainGradeProduct(){
        return getGradeProducts().stream()
                .sorted(Comparator.comparingInt(p -> (new BigDecimal(p.getProductPercentage()).multiply(new BigDecimal(100))).intValue()))
                .findFirst().orElse(null);
    }
}
