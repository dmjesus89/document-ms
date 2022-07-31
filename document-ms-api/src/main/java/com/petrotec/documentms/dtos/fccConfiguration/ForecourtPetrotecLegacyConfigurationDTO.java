package com.petrotec.documentms.dtos.fccConfiguration;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class ForecourtPetrotecLegacyConfigurationDTO {

    private FccCommunicationCfgDTO commCfg;
    private FccPmpCfgDTO pmpCfg;
    private FccUnitsCfgDTO unitsCfg;

    public ForecourtPetrotecLegacyConfigurationDTO() {
    }

    public FccCommunicationCfgDTO getCommCfg() {
        return commCfg;
    }

    public void setCommCfg(FccCommunicationCfgDTO commCfg) {
        this.commCfg = commCfg;
    }

    public FccPmpCfgDTO getPmpCfg() {
        return pmpCfg;
    }

    public void setPmpCfg(FccPmpCfgDTO pmpCfg) {
        this.pmpCfg = pmpCfg;
    }

    public FccUnitsCfgDTO getUnitsCfg() {
        return unitsCfg;
    }

    public void setUnitsCfg(FccUnitsCfgDTO unitsCfg) {
        this.unitsCfg = unitsCfg;
    }
}
