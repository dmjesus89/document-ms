package com.petrotec.documentms.dtos.siteGroup;

import com.petrotec.documentms.dtos.site.SiteDTO;

import java.time.LocalDateTime;
import java.util.Objects;

/** SiteGroupItemSiteDTO */
public class SiteGroupItemSiteDTO {

    private SiteDTO site;

    private SiteGroupItemDTO siteGroupItem;

    private LocalDateTime createdOn;

    public SiteGroupItemSiteDTO() {
    }

    public SiteGroupItemSiteDTO(SiteDTO site, SiteGroupItemDTO siteGroupItem, LocalDateTime createdOn) {
        this.site = site;
        this.siteGroupItem = siteGroupItem;
        this.createdOn = createdOn;
    }

    public SiteDTO getSite() {
        return this.site;
    }

    public void setSite(SiteDTO site) {
        this.site = site;
    }

    public SiteGroupItemDTO getSiteGroupItem() {
        return this.siteGroupItem;
    }

    public void setSiteGroupItem(SiteGroupItemDTO siteGroupItem) {
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
        if (!(o instanceof SiteGroupItemSiteDTO)) {
            return false;
        }
        SiteGroupItemSiteDTO siteGroupItemSiteDTO = (SiteGroupItemSiteDTO) o;
        return Objects.equals(site, siteGroupItemSiteDTO.site)
                && Objects.equals(siteGroupItem, siteGroupItemSiteDTO.siteGroupItem)
                && Objects.equals(createdOn, siteGroupItemSiteDTO.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(site, siteGroupItem, createdOn);
    }

    @Override
    public String toString() {
        // @formatter:off
        return "{" +
            " site='" + getSite() + "'" +
            ", siteGroupItem='" + getSiteGroupItem() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
        // @formatter:on
    }

}