package com.petrotec.documentms.entities;

import com.petrotec.documentms.entities.product.Product;

import javax.persistence.*;

@Entity
@Table(name = "site_product")
public class SiteProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "fcc_product_code")
    private String fccProductCode;

    public SiteProduct() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getFccProductCode() {
        return fccProductCode;
    }

    public void setFccProductCode(String fccProductCode) {
        this.fccProductCode = fccProductCode;
    }
}
