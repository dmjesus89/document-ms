package com.petrotec.documentms.services;

import com.petrotec.documentms.dtos.site.SiteDTO;
import com.petrotec.documentms.dtos.siteConfiguration.*;
import com.petrotec.documentms.entities.SiteDevice;
import com.petrotec.documentms.mappers.SiteMapper;
import io.micronaut.transaction.jdbc.DataSourceTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * SiteService
 */
@Singleton
@Transactional
public class SiteConfigurationService {
    private static final Logger LOG = LoggerFactory.getLogger(SiteConfigurationService.class);

    private final Connection connection;
    private final DataSourceTransactionManager transactionManager;
    private final SiteService siteService;
    private final SiteMapper siteMapper;
    private final SiteDeviceService siteDeviceService;

    public SiteConfigurationService(Connection connection, DataSourceTransactionManager transactionManager,
            SiteService siteService, SiteMapper siteMapper, SiteDeviceService siteDeviceService) {
        this.connection = connection;
        this.transactionManager = transactionManager;
        this.siteService = siteService;
        this.siteMapper = siteMapper;
        this.siteDeviceService = siteDeviceService;
    }

    private static String PUMP_QUERY =
            "SELECT\n" +
                    "    site_device.code AS code,\n" +
                    "    site_device.description AS description,\n" +
                    "    site_device_fuel_point.pump_number AS pump_number,\n" +
                    "    site_device.is_enabled AS is_enabled\n" +
                    "FROM\n" +
                    "    site\n" +
                    "INNER JOIN site_device ON\n" +
                    "    site_device.site_id = site.id\n" +
                    "INNER JOIN site_device_fuel_point ON\n" +
                    "    site_device_fuel_point.id = site_device.id\n" +
                    "WHERE site.code = ?";

    private static String NOZZLE_QUERY =
            "SELECT\n" +
                    "\tsite_device.code as `pump_code`,\n" +
                    "\tsite_device_fuel_point.pump_number as `pump_number`,\n" +
                    "\tsite_device_fuel_point_nozzle.nozzle_number as `nozzle_number`,\n" +
                    "\twarehouse_device.code as `warehouse_code`,\n" +
                    "\tgrade.code as `grade_code`,\n" +
                    "\tproduct.code as `product_code`,\n" +
                    "\tsite_device_warehouse.fcc_warehouse_code as `fcc_warehouse_code`,\n" +
                    "\tsite_product.fcc_product_code as `fcc_product_code`,\n" +
                    "\tsite_grade.fcc_grade_code as `fcc_grade_code`\n" +
                    "FROM\n" +
                    "    site\n" +
                    "INNER JOIN site_device ON\n" +
                    "    site_device.site_id = site.id\n" +
                    "INNER JOIN site_device_fuel_point ON\n" +
                    "    site_device_fuel_point.id = site_device.id\n" +
                    "INNER JOIN site_device_fuel_point_nozzle ON\n" +
                    "    site_device_fuel_point_nozzle.site_device_id = site_device_fuel_point.id\n" +
                    "INNER JOIN site_device_warehouse ON\n" +
                    "    site_device_warehouse.id = site_device_fuel_point_nozzle.site_device_warehouse_id \n" +
                    "INNER JOIN site_device as warehouse_device ON\n" +
                    "    site_device_warehouse.id = warehouse_device.id \n" +
                    "INNER JOIN site_grade ON\n" +
                    "     site_grade.id = site_device_fuel_point_nozzle.site_grade_id\n" +
                    "LEFT JOIN grade ON\n" +
                    "     site_grade.grade_id = grade.id  AND site_grade.site_id = site.id\n" +
                    "LEFT JOIN grade_product ON\n" +
                    "     grade_product.grade_id = grade.id\n" +
                    "LEFT JOIN product ON\n" +
                    "     product.id = grade_product.product_id \n" +
                    "LEFT JOIN site_product ON\n" +
                    "     product.id = site_product.product_id AND site_product.site_id = site.id \n" +
                    "WHERE site.code = ?";

    private static String WAREHOUSE_QUERY = "SELECT \n" +
            "\tsite_device.code as `code`,\n" +
            "\tsite_device.description as `description`,\n" +
            "\tsite_device_warehouse.fcc_warehouse_code as `fcc_warehouse_code`,\n" +
            "\tproduct.code as `product_code`,\n" +
            "\twarehouse_type.code as `type_code`,\n" +
            "\tsite_device.is_enabled AS is_enabled\n" +
            "FROM\n" +
            "    site\n" +
            "INNER JOIN site_device ON\n" +
            "    site_device.site_id = site.id\n" +
            "INNER JOIN site_device_warehouse ON\n" +
            "    site_device_warehouse.id = site_device.id \n" +
            "INNER JOIN warehouse_type ON\n" +
            "    site_device_warehouse.warehouse_type_id = warehouse_type.id \n" +
            "INNER JOIN product ON\n" +
            "\tsite_device_warehouse.product_id = product.id\n" +
            "WHERE site.code = ?";

    private static String GRADE_QUERY = "SELECT \n" +
            "\tgrade.code as `code`,\n" +
            "\tgrade.description as `description`,\n" +
            "\tsite_grade.fcc_grade_code as `fcc_grade_code`,\n" +
            "\tproduct.code as `product_code`,\n" +
            "\tgrade_product.product_percentage as `product_percentage` \n" +
            "FROM site_grade \n" +
            "LEFT JOIN site ON site.id = site_grade.site_id\n" +
            "LEFT JOIN grade ON grade.id = site_grade.grade_id \n" +
            "LEFT JOIN grade_product ON grade.id = grade_product.grade_id\n" +
            "LEFT JOIN product ON product.id = grade_product.product_id\n" +
            "WHERE site.code = ?";

    private static String PRODUCT_QUERY = "SELECT \n" +
            "\tproduct.code as `product_code`,\n" +
            "\tsite_product.fcc_product_code as `fcc_product_code`,\n" +
            "\tproduct.description as `description`,\n" +
            "\tproduct_group.code as `product_group_code`,\n" +
            "\ttranslate(product_group.description,'pt-pt') as `product_group_description`\n" +
            "FROM site_product \n" +
            "LEFT JOIN site on site_product.site_id = site.id\n" +
            "LEFT JOIN product on site_product.product_id = product.id\n" +
            "LEFT JOIN product_group on product.product_group_id = product_group.id\n" +
            "WHERE site.code = ?";


    public SiteConfigurationExtendedDTO getSiteConfiguration(String siteCode) {

        SiteDTO siteDTO = siteMapper.toDTO(siteService.getSiteByCode(siteCode), null);

        SiteConfigurationExtendedDTO result = new SiteConfigurationExtendedDTO();
        result.setSite(siteDTO);
        result.setProducts(getSiteProducts(siteCode));
        result.setPumps(getSitePumps(siteCode));
        result.setWarehouses(getSiteWarehouses(siteCode));
        result.setGrades(getSiteGrades(siteCode));
        result.setFcc(getFcc(siteCode));

        fillNozzleData(result);

        return result;
    }

    private Map<String, SiteConfigurationExtendedPumpDTO> getSitePumps(String siteCode) {
        return transactionManager.executeRead(status -> {
            Map<String, SiteConfigurationExtendedPumpDTO> pumps = new HashMap<>();

            try (PreparedStatement ps = connection.prepareStatement(PUMP_QUERY)) {
                ps.setString(1, siteCode);
                final ResultSet rs = ps.executeQuery();
                while (rs != null && rs.next()) {
                    SiteConfigurationExtendedPumpDTO p = new SiteConfigurationExtendedPumpDTO();
                    p.setCode(rs.getString("code"));
                    p.setDescription(rs.getString("description"));
                    p.setPumpNumber(rs.getInt("pump_number"));
                    p.setNozzles(new HashMap<>());
                    p.setEnabled(rs.getBoolean("is_enabled"));

                    pumps.put(p.getCode(), p);
                }
            }
            return pumps;
        });
    }

    private void fillNozzleData(final SiteConfigurationExtendedDTO siteConf) {
        transactionManager.executeRead(status -> {
            try (PreparedStatement ps = connection.prepareStatement(NOZZLE_QUERY)) {
                ps.setString(1, siteConf.getSite().getCode());
                final ResultSet rs = ps.executeQuery();
                while (rs != null && rs.next()) {
                    SiteConfigurationExtendedNozzleDTO n = new SiteConfigurationExtendedNozzleDTO();
                    n.setNozzleNumber(rs.getInt("nozzle_number"));
                    n.setWarehouseCode(rs.getString("warehouse_code"));
                    n.setFccWarehouseCode(rs.getString("fcc_warehouse_code"));
                    n.setFccProductCode(rs.getString("fcc_product_code"));
                    n.setFccGradeCode(rs.getString("fcc_grade_code"));


                    n.setGradeCode(rs.getString("grade_code"));
                    n.setProductCode(rs.getString("product_code"));

                    Integer pumpNumber = rs.getInt("pump_number");

                    SiteConfigurationExtendedPumpDTO pump = siteConf.getPumps().values().stream().filter(p -> p.getPumpNumber() == pumpNumber).findAny().orElse(null);

                    if (pump != null) {
                        pump.getNozzles().put(n.getNozzleNumber(), n);
                    } else {
                        LOG.error("Nozzle has pump number {} configured, but that pump number doesn't exist on the site");
                    }
                }
            }
            return null;
        });
    }

    private Map<String, SiteConfigurationExtendedProductDTO> getSiteProducts(String siteCode) {
        return transactionManager.executeRead(status -> {
            Map<String, SiteConfigurationExtendedProductDTO> products = new HashMap<>();
            try (PreparedStatement ps = connection.prepareStatement(PRODUCT_QUERY)) {
                ps.setString(1, siteCode);
                final ResultSet rs = ps.executeQuery();
                while (rs != null && rs.next()) {
                    SiteConfigurationExtendedProductDTO p = new SiteConfigurationExtendedProductDTO();
                    p.setCode(rs.getString("product_code"));
                    p.setFccProductCode(rs.getString("fcc_product_code"));
                    p.setDescription(rs.getString("description"));
                    p.setProductGroupCode(rs.getString("product_group_code"));
                    p.setProductGroupDescription(rs.getString("product_group_description"));

                    products.put(rs.getString("product_code"), p);
                }
            }
            return products;
        });
    }

    private Map<String, SiteConfigurationExtendedWarehouseDTO> getSiteWarehouses(String siteCode) {
        return transactionManager.executeRead(status -> {
            Map<String, SiteConfigurationExtendedWarehouseDTO> warehouses = new HashMap<>();
            try (PreparedStatement ps = connection.prepareStatement(WAREHOUSE_QUERY)) {
                ps.setString(1, siteCode);
                final ResultSet rs = ps.executeQuery();
                while (rs != null && rs.next()) {
                    SiteConfigurationExtendedWarehouseDTO w = new SiteConfigurationExtendedWarehouseDTO();
                    w.setCode(rs.getString("code"));
                    w.setDescription(rs.getString("description"));
                    w.setFccWarehouseCode(rs.getString("fcc_warehouse_code"));
                    w.setProductCode(rs.getString("product_code"));
                    w.setTypeCode(rs.getString("type_code"));
                    w.setEnabled(rs.getBoolean("is_enabled"));

                    warehouses.put(w.getCode(), w);
                }
            }
            return warehouses;
        });
    }

    private Map<String, SiteConfigurationExtendedGradeDTO> getSiteGrades(String siteCode) {
        return transactionManager.executeRead(status -> {
            Map<String, SiteConfigurationExtendedGradeDTO> grades = new HashMap<>();
            try (PreparedStatement ps = connection.prepareStatement(GRADE_QUERY)) {
                ps.setString(1, siteCode);
                final ResultSet rs = ps.executeQuery();
                while (rs != null && rs.next()) {
                    SiteConfigurationExtendedGradeDTO g = new SiteConfigurationExtendedGradeDTO();
                    g.setCode(rs.getString("code"));
                    g.setDescription(rs.getString("description"));
                    g.setFccGradeCode(rs.getInt("fcc_grade_code"));
                    g.setProductCode(rs.getString("product_code"));
                    g.setProductPercentage(rs.getInt("product_percentage"));

                    grades.put(g.getCode(), g);
                }
            }
            return grades;
        });
    }


    private SiteConfigurationExtendedDTO.FccCommunicationPetrotecConfigDTO getFcc(String siteCode) {
        SiteDevice fcc = siteDeviceService.listAllFccDevicesBySiteDTO(siteCode).stream().filter(siteDevice -> siteDevice.getDeviceType().getCode().equals("FCC")).findFirst().get();
        SiteConfigurationExtendedDTO.FccCommunicationPetrotecConfigDTO result = new SiteConfigurationExtendedDTO.FccCommunicationPetrotecConfigDTO();
        result.setForecourtClientInterface(SiteConfigurationExtendedDTO.FccCommunicationPetrotecConfigDTO.FccTypeEnum.valueOf(fcc.getDeviceSubtype().getCode()));
        result.setAdditionalData(fcc.getAdditionalData());
        return result;
    }
}
