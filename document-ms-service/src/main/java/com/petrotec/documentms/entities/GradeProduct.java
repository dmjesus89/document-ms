package com.petrotec.documentms.entities;

import com.petrotec.documentms.entities.product.Product;

import javax.persistence.*;

@Entity
@Table(name = "grade_product")
public class GradeProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grade_id", referencedColumnName = "id", nullable = false)
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(name = "product_percentage")
    private String productPercentage;

    public GradeProduct() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductPercentage() {
        return productPercentage;
    }

    public void setProductPercentage(String productPercentage) {
        this.productPercentage = productPercentage;
    }
}
