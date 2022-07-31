package com.petrotec.documentms.services;

import com.petrotec.documentms.entities.EntitySite;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.repositories.interfaces.EntitySiteRepository;
import com.petrotec.documentms.repositories.SiteRepository;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EntityService
 */
@Singleton
@Transactional
public class EntityService {

    private final EntitySiteRepository entitySiteRepository;
    private final SiteRepository siteRepository;

    public EntityService(EntitySiteRepository entitySiteRepository, SiteRepository siteRepository) {
        this.entitySiteRepository = entitySiteRepository;
        this.siteRepository = siteRepository;
    }

    public Collection<String> getEntitySiteCodes(String entityCode) {
        return getEntitySites(entityCode).stream().map(EntitySite::getSiteCode).collect(Collectors.toList());
    }

    public Collection<EntitySite> getEntitySites(String entityCode) {
        List<Site> byEntityCode = siteRepository.findByEntityCode(entityCode);
        List<EntitySite> result = byEntityCode.stream().map(site -> {
            EntitySite entitySite = new EntitySite();
            entitySite.setSiteCode(site.getCode());
            entitySite.setEntityCode(site.getEntityCode());
            return entitySite;
        }).collect(Collectors.toList());
        return result;
    }

}
