package com.petrotec.documentms.entities;

import javax.persistence.*;

@Entity
@Table(name = "grade_site_device_price_sign")
public class GradePriceSign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grade_id", referencedColumnName = "id", nullable = false)
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "site_device_price_sign_id", referencedColumnName = "id", nullable = false)
    private SiteDevicePriceSign siteDevicePriceSign;

    @Column(name = "grade_order")
    private int gradeOrder;

    public GradePriceSign() {
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

    public SiteDevicePriceSign getSiteDevicePriceSign() {
        return siteDevicePriceSign;
    }

    public void setSiteDevicePriceSign(SiteDevicePriceSign siteDevicePriceSign) {
        this.siteDevicePriceSign = siteDevicePriceSign;
    }

    public int getGradeOrder() {
        return gradeOrder;
    }

    public void setGradeOrder(Integer gradeOrder) {
        if (gradeOrder != null) this.gradeOrder = gradeOrder;
    }
}
