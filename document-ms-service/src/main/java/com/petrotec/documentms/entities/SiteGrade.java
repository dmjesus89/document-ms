package com.petrotec.documentms.entities;

import javax.persistence.*;

@Entity
@Table(name = "site_grade")
public class SiteGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fcc_grade_code")
    private Short fccGradeCode;

    @ManyToOne
    @JoinColumn(name = "site_id", referencedColumnName = "id", nullable = false)
    private Site site;

    @ManyToOne
    @JoinColumn(name = "grade_id", referencedColumnName = "id", nullable = false)
    private Grade grade;

    public SiteGrade() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getFccGradeCode() {
        return fccGradeCode;
    }

    public void setFccGradeCode(Short fccGradeCode) {
        this.fccGradeCode = fccGradeCode;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
