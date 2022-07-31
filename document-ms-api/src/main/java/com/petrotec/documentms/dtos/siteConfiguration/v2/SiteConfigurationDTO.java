package com.petrotec.documentms.dtos.siteConfiguration.v2;

import com.petrotec.documentms.dtos.configuration.FuellingModeDTO;
import com.petrotec.documentms.dtos.site.ServiceModeDTO;
import com.petrotec.documentms.dtos.siteDevice.WarehouseTypeDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Site configuration data")
public class SiteConfigurationDTO {

    @Schema(description = "Site generic data")
    private SiteConfigurationSiteDTO site;
    @Schema(description = "Site product Mapping. <ProductCode -> Product>")
    private Map<String, SiteConfigurationProductDTO> products;
    @Schema(description = "Site grade Mapping. <GradeCode -> Grade>")
    private Map<String, SiteConfigurationGradeDTO> grades;
    @Schema(description = "Site fuel Points")
    private Map<String, SiteConfigurationFuelPointDTO> fuelPoints;
    @Schema(description = "Site warehouses")
    private Map<String, SiteConfigurationWarehouseDTO> warehouses;
    @Schema(description = "Site pos")
    private Map<String, SiteConfigurationPosDTO> pos;
    @Schema(description = "Site fcc")
    private Map<String, SiteConfigurationFccDTO> fcc;
    @Schema(description = "Site fuel Dispensers")
    private Map<String, SiteConfigurationFuelDispenserDTO> fuelDispensers;
    @Schema(description = "Site tank level gauges")
    private Map<String, SiteConfigurationTankLevelGaugesDTO> tankLevelGauges;
    @Schema(description = "Site price Signs")
    private Map<String, SiteConfigurationPriceSignsDTO> priceSigns;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SiteConfigurationSiteDTO {
        private String code;
        private String siteNumber;
        private String entityCode;
        private String latitude;
        private String longitude;
        private boolean enabled;
        private Map<String, String> description;
        private String regionCode;
        private Map<String, String> regionDescription;
        private String siteProfileCode;
        private Map<String, String> siteProfileDescription;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SiteConfigurationProductDTO {
        private String code;

        @Schema(description = "code to be used on fcc configuration")
        private int fccProductCode;

        private BigDecimal unitPrice;
        
        private Map<String, String> description;
        private String color;
        private String productGroupCode;
        private String productGroupColor;
        private String productGroupDescription;
        private String productFamilyCode;
        private String productFamilyColor;
        private String productFamilyDescription;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SiteConfigurationGradeDTO {
        private String code;
        private String color;
        private BigDecimal unitPrice;

        @Schema(description = "code to be used on fcc configuration")
        private int fccGradeCode;
        private Map<String, String> description;

        @Schema(description = "Main product code")
        private String productCode;
        @Schema(description = "Main product percentage")
        private int productPercentage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SiteConfigurationFuelPointGradeDTO {
        private String code;

        @Schema(description = "code to be used on fcc configuration")
        private int nozzleNumber;

        @Schema(description = "Grade unique code (uuid) associated to the current nozzle")
        private String gradeCode;

        @Schema(description = "Warehouse unique code (uuid) associated to the current nozzle")
        private String warehouseCode;

        @Schema(description = "Main product code")
        private String productCode;

        @Schema(description = "Main product percentage")
        private int productPercentage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SiteConfigurationFuelPointDTO {
        private String code;

        @Schema(description = "code to be used on fcc configuration")
        private int pumpNumber;

        private String description;

        private boolean enabled = true;

        private String protocolTypeCode;
        private String communicationMethodTypeCode;
        private Object communicationMethodData;

        @Schema(description = "fcc unique code (uuid) responsible for controlling current fuel point")
        private String fccCode;

        @Schema(description = "fuel dispenser unique code (uuid) associated to the current fuel point")
        private String fuelDispenserCode;


        private List<SiteConfigurationFuelPointGradeDTO> nozzles = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SiteConfigurationWarehouseDTO {
        private String code;

        private boolean enabled;

        @Schema(description = "code to be used on fcc configuration")
        private int warehouseCode;

        private String description;

        private String productCode;

        private WarehouseTypeDTO.WAREHOUSE_TYPE warehouseTypeCode;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SiteConfigurationFuelDispenserDTO {
        private String code;

        private boolean enabled;

        @Schema(description = "code to be used on fcc configuration")
        private int dispenserNumber;

        private String description;

        @Schema(description = "List of fuel points associated to this fuel dispenser")
        private List<String> fuelPoints = new ArrayList<>();

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SiteConfigurationPosVirtualDTO {
        private String code;

        @Schema(description = "code to be used on fcc configuration")
        private int virtualPosNumber;

        private String description;

        private boolean hasPrinter;

        @Schema(description = "List of fuel points associated to this pos")
        private List<String> fuelPoints = new ArrayList<>();

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SiteConfigurationPosDTO {
        private String code;

        private boolean enabled;

        @Schema(description = "code to be used on fcc configuration")
        private int posNumber;

        private String description;


        private String protocolTypeCode;
        private String communicationMethodTypeCode;
        private Object communicationMethodData;

        @Schema(description = "List of fuel points associated to this pos")
        private List<String> fuelPoints = new ArrayList<>();

        private List<SiteConfigurationPosVirtualDTO> virtualPOS = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SiteConfigurationTankLevelGaugesDTO {
        private String code;

        private boolean enabled;

        @Schema(description = "code to be used on fcc configuration")
        private int tlgCode;

        private String description;

        private String warehouseCode;

        private String protocolTypeCode;
        private String communicationMethodTypeCode;
        private Object communicationMethodData;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SiteConfigurationPriceSignsDTO {
        private String code;

        private boolean enabled;

        @Schema(description = "code to be used on fcc configuration")
        private int priceSignCode;

        private String description;

        private String protocolTypeCode;
        private String communicationMethodTypeCode;
        private Object communicationMethodData;

        @Schema(description = "List of grades associated to this pos")
        private Map<Integer, String> grades = new HashMap<>();

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SiteConfigurationFccDTO {
        private String code;
        private boolean enabled;
        private String description;
        private Map<String, Object> additionalData;

        private String protocolTypeCode;
        private String communicationMethodTypeCode;
        private Object communicationMethodData;

        @Schema(description = "List of fuel points associated to this pos")
        private List<String> fuelPoints = new ArrayList<>();

        @Schema(description = "List of fuel points associated to this pos")
        private List<String> tlgs = new ArrayList<>();

        @Schema(description = "List of price signs associated to this pos")
        private List<String> priceSigns = new ArrayList<>();

        @Schema(description = "List of price signs associated to this pos")
        private List<String> pos = new ArrayList<>();

        private ServiceModeDTO serviceMode;

        private FuellingModeDTO fuellingMode;

    }

}
