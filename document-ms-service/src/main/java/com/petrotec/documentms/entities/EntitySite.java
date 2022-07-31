package com.petrotec.documentms.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EntitySite
 */
@Entity
@Table(name = "entity_site")
public class EntitySite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "site_code")
    public String siteCode;

    @Column(name = "entity_code")
    public String entityCode;

    public EntitySite() {
    }

    public EntitySite(int id, String siteCode, String entityCode) {
        this.id = id;
        this.siteCode = siteCode;
        this.entityCode = entityCode;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteCode() {
        return this.siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof EntitySite)) {
            return false;
        }
        EntitySite entitySite = (EntitySite) o;
        return id == entitySite.id && Objects.equals(siteCode, entitySite.siteCode)
                && Objects.equals(entityCode, entitySite.entityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, siteCode, entityCode);
    }

    @Override
    public String toString() {
        return "{ id='" + getId() + "'" + ", site='" + getSiteCode() + "'" + ", entityCode='" + getEntityCode() + "' }";
    }

}