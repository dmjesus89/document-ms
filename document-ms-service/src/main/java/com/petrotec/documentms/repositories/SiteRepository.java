package com.petrotec.documentms.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.api.filter.translator.JdbcStatementHelper;
import com.petrotec.categories.services.CustomBaseDTOService;
import com.petrotec.multitenant.repository.MultitenantLazyRepository;
import com.petrotec.multitenant.resolver.MultitenantResolver;
import com.petrotec.documentms.dtos.site.SiteCustomDTO;
import com.petrotec.documentms.dtos.site.SiteDeviceDataDTO;
import com.petrotec.documentms.dtos.siteProfile.SiteProfileDTO;
import com.petrotec.documentms.dtos.siteRegion.RegionDTO;
import com.petrotec.documentms.entities.Site;
import io.micronaut.context.BeanLocator;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.transaction.SynchronousTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/** SiteRepositoryImpl */
@Repository
@Transactional
public abstract class SiteRepository extends MultitenantLazyRepository implements JpaRepository<Site,Long> {

    private static final Logger LOG = LoggerFactory.getLogger(SiteRepository.class);

    private static boolean firstRun = true;
    private int iteration = 0;

    @Inject
    private Connection connection;

    @Inject
    private SynchronousTransactionManager<Connection> transactionManager;

    @Inject
    private CustomBaseDTOService customBaseService;

    @Inject
    private ObjectMapper objectMapper;

    private static final String NATIVE_QUERY = "SELECT site.id as 'id', site.code as 'code', site.site_number as 'site_number'," +
            " translate(site.description, '@@lang_code@@') AS description, site.description as detailed_description, r.code as 'region.code'," +
            " translate(r.description, '@@lang_code@@') AS 'region.description', sp.id as 'site_profile.id', sp.code as 'site_profile.code'," +
            " translate(sp.description, '@@lang_code@@') as 'site_profile.description', site.is_enabled as enabled, site.latitude, site.longitude,\n" +
            "\t(Select count(*) from site_device where site_id = site.id and site_device.site_device_type_id = (SELECT id FROM site_device_type sdt where code = 'FUEL_POINT')) as `site_devices.n_fuel_points`,\n" +
            "\t(Select count(*) from site_device where site_id = site.id and site_device.site_device_type_id = (SELECT id FROM site_device_type sdt where code = 'DISPENSER')) as `site_devices.n_dispensers`,\n" +
            "\t(Select count(*) from site_device where site_id = site.id and site_device.site_device_type_id = (SELECT id FROM site_device_type sdt where code = 'POS')) as `site_devices.n_pos`,\n" +
            "\t(Select count(*) from site_device where site_id = site.id and site_device.site_device_type_id = (SELECT id FROM site_device_type sdt where code = 'WAREHOUSE')) as `site_devices.n_warehouses`,\n" +
            "\t(Select count(*) from site_device where site_id = site.id and site_device.site_device_type_id = (SELECT id FROM site_device_type sdt where code = 'FCC')) as `site_devices.n_fccs`, site.created_on as created_on, site.created_on as updated_on "
            + "    @properties@ \r\n"
            + "    @categories@ \r\n"
            + " FROM site AS site INNER JOIN region as r ON site.region_id = r.id INNER JOIN site_profile as sp ON sp.id = site.site_profile_id";

    public SiteRepository(BeanLocator beanLocator, MultitenantResolver multitenantResolver) {
        super(beanLocator, multitenantResolver);
    }

    public Optional<Site> findByCode(@NotBlank String code) {
        String qString = "SELECT s FROM com.petrotec.documentms.entities.Site as s WHERE s.code = :code";
        try {
            Site site = getEntityManager().createQuery(qString, Site.class).setParameter("code", code)
                    .getSingleResult();
            return Optional.of(site);
        } catch (NoResultException noResultException) {
            return Optional.empty();
        }
    }

    public Optional<Site> findByCodeAndEnabled(@NotBlank String code) {
        String qString = "SELECT s FROM com.petrotec.documentms.entities.Site as s WHERE s.code = :code AND s.enabled = true";
        try {
            Site site = getEntityManager().createQuery(qString, Site.class).setParameter("code", code)
                    .getSingleResult();
            return Optional.of(site);
        } catch (NoResultException noResultException) {
            return Optional.empty();
        }
    }

    @Override
    public Site save(@NotBlank Site site) {
        getEntityManager().persist(site);
        getEntityManager().flush();
        getEntityManager().refresh(site);
        return site;
    }

    public int enabled(@NotEmpty String code, boolean enabled) {
        return getEntityManager().createQuery("UPDATE Site s SET is_enabled = :enabled WHERE code = :code")
                .setParameter("enabled", enabled).setParameter("code", code).executeUpdate();
    }

    public List<Site> update(List<Site> sites) {
        List<Site> updatedSites = sites.stream().map(site -> update(site)).filter(Objects::nonNull)
                .collect(Collectors.toList());
        return updatedSites;
    }

    protected String buildQuery(String locale){
        final String siteProperties = customBaseService.getProperties(CustomBaseDTOService.PROPERTY_USAGE.SITE,".","");
        final String siteCategories = customBaseService.getCategories(CustomBaseDTOService.CATEGORY_USAGE.SITE,".","");


        String query = NATIVE_QUERY
                .replaceAll("@properties@", siteProperties)
                .replaceAll("@categories@", siteCategories)
                .replaceAll("@@lang_code@@", locale);

        return query;
    }


    public PageResponse<SiteCustomDTO> findAll(@Nullable final PageAndSorting pageAndSorting,
                                               @Nullable final Filter filters, String locale) {
        long requestStartedTime = System.currentTimeMillis();

        final int limit = pageAndSorting.getLimit() + 1;

        final JdbcStatementHelper helper = new JdbcStatementHelper();
        final List<SiteCustomDTO> sites = new ArrayList<SiteCustomDTO>();

        String query = buildQuery(locale);
        transactionManager.executeRead(status -> {
            LOG.debug("Starting to process query:{}",System.currentTimeMillis() - requestStartedTime);
            try (PreparedStatement ps = super.generateStatement(connection, query, filters, null, pageAndSorting.getOffset(), limit, pageAndSorting.getSorting(),
                    SiteCustomDTO.class, helper.getParameterValues().toArray())) {
                final ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    try {
                        SiteCustomDTO vehicle = mapResultSet(rs);
                        sites.add(vehicle);
                    } catch (Exception ex) {
                        LOG.error(ex.getMessage(),ex);

                    }
                }
            }catch (Exception e){
                LOG.error(e.getMessage(),e);
            }
            return "";

        });

        String countQuery = filters == null ? ("SELECT * FROM site ") : query;
        return PageResponse.from(sites, pageAndSorting, () -> countResult(countQuery,filters, SiteCustomDTO.class));
    }

    private SiteCustomDTO mapResultSet(ResultSet rs) throws SQLException {
        SiteCustomDTO siteCustom = new SiteCustomDTO();

        RegionDTO region = new RegionDTO();
        siteCustom.setRegion(region);

        SiteProfileDTO siteProfile = new SiteProfileDTO();
        siteCustom.setSiteProfile(siteProfile);

        SiteDeviceDataDTO siteDevices = new SiteDeviceDataDTO();
        siteCustom.setSiteDevices(siteDevices);

        siteCustom.setCode(rs.getString("code"));
        siteCustom.setDescription(rs.getString("description"));

        String siteDetailedDescription = rs.getString("detailed_description");
        if (siteDetailedDescription != null){
            try {
                siteCustom.setDetailedDescription(objectMapper.readValue(siteDetailedDescription, Map.class));
            }catch (Exception ex){
                LOG.error("error reading site description", ex);
            }
        }



        region.setDescription(rs.getString("region.description"));
        region.setCode(rs.getString("region.code"));

        siteProfile.setDescription(rs.getString("site_profile.description"));
        siteProfile.setCode(rs.getString("site_profile.code"));

        siteCustom.setLatitude(rs.getString("latitude"));
        siteCustom.setLongitude(rs.getString("longitude"));
        siteCustom.setEnabled(rs.getBoolean("enabled"));
        siteCustom.setSiteNumber(rs.getString("site_number"));

        siteDevices.setnFuelPoints(rs.getInt("site_devices.n_fuel_points"));
        siteDevices.setnDispensers(rs.getInt("site_devices.n_dispensers"));
        siteDevices.setnPos(rs.getInt("site_devices.n_pos"));
        siteDevices.setnWarehouses(rs.getInt("site_devices.n_warehouses"));
        siteDevices.setnFccs(rs.getInt("site_devices.n_fccs"));

        if (rs.getTimestamp("updated_on") != null)
            siteCustom.setUpdatedOn(rs.getTimestamp("updated_on").toLocalDateTime());

        if (rs.getTimestamp("created_on") != null)
            siteCustom.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());


        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        int columnIndex = 1;
        for (columnIndex = 1; columnIndex < columnCount; columnIndex++) {
            if (meta.getColumnName(columnIndex).equals("updated_on")) {
                columnIndex++;
                break;
            }
        }

        columnIndex = customBaseService.fillCustomDtoProperties(columnIndex, columnCount, rs, siteCustom,".","");
        columnIndex = customBaseService.fillCustomDtoCategories(columnIndex, columnCount, rs, siteCustom,".","");

        return siteCustom;
    }


    public long count(Filter filters, String locale) {
        return countResult(getEntityManager(),buildQuery(locale), filters, null);
    }

    public List<Site> findByCode(List<String> codes) {
        String qString = "SELECT s FROM com.petrotec.documentms.entities.Site as s WHERE s.code IN :codes";
        return getEntityManager().createQuery(qString, Site.class).setParameter("codes", codes).getResultList();
    }

    public List<Site> findByCodeAndEnabled(List<String> codes) {
        String qString = "SELECT s FROM com.petrotec.documentms.entities.Site as s WHERE s.code IN :codes AND s.enabled = true";
        return getEntityManager().createQuery(qString, Site.class).setParameter("codes", codes).getResultList();
    }

    public List<Site> findByEntityCode(String entityCode) {
        String qString = "SELECT s FROM com.petrotec.documentms.entities.Site as s WHERE s.entityCode IN :entityCode AND s.enabled = true";
        return getEntityManager().createQuery(qString, Site.class).setParameter("entityCode", entityCode).getResultList();
    }

    public abstract Optional<Site> findBySiteNumber(String siteNumber);
}
