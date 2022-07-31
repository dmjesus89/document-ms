package com.petrotec.documentms.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "virtual_pos")
public class VirtualPos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description", length = 45)
    private String description;

    @Column(name = "has_printer")
    boolean hasPrinter = true;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "site_device_pos_id", referencedColumnName = "id")
    private SiteDevicePos siteDevicePos;

    @OneToMany(fetch = FetchType.LAZY)
    private List<SiteDeviceFuelPoint> fuelPoints = new ArrayList<>();

    @Column(name = "number")
    private Integer number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasPrinter() {
        return hasPrinter;
    }

    public void setHasPrinter(boolean hasPrinter) {
        this.hasPrinter = hasPrinter;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public SiteDevicePos getSiteDevicePos() {
        return siteDevicePos;
    }

    public void setSiteDevicePos(SiteDevicePos siteDevicePos) {
        this.siteDevicePos = siteDevicePos;
    }
}
