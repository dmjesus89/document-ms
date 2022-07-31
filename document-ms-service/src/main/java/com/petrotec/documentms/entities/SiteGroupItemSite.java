package com.petrotec.documentms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

/** SiteGroupItemSite */

@Entity
@Table(name = "site_group_item_site", uniqueConstraints = @UniqueConstraint(columnNames = { "site_id",
        "site_group_item_id" }))
public class SiteGroupItemSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "site_id", referencedColumnName = "id", unique = true)
    private Site site;

    @ManyToOne(optional = false)
    @JoinColumn(name = "site_group_item_id", referencedColumnName = "id", unique = true)
    private SiteGroupItem siteGroupItem;

    @Column(name = "created_on", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Site getSite() {
        return this.site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public SiteGroupItem getSiteGroupItem() {
        return this.siteGroupItem;
    }

    public void setSiteGroupItem(SiteGroupItem siteGroupItem) {
        this.siteGroupItem = siteGroupItem;
    }

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof SiteGroupItemSite)) {
            return false;
        }
        SiteGroupItemSite siteGroupItemSite = (SiteGroupItemSite) o;
        return id == siteGroupItemSite.id && Objects.equals(site, siteGroupItemSite.site)
                && Objects.equals(siteGroupItem, siteGroupItemSite.siteGroupItem)
                && Objects.equals(createdOn, siteGroupItemSite.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, site, siteGroupItem, createdOn);
    }

    @Override
    public String toString() {
        // @formatter:off
        return "{" +
            " id='" + getId() + "'" +
            ", site='" + getSite() + "'" +
            ", siteGroupItem='" + getSiteGroupItem() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
        // @formatter:on
    }

}