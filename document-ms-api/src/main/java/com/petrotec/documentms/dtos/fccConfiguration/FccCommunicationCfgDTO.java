package com.petrotec.documentms.dtos.fccConfiguration;

public class FccCommunicationCfgDTO {

    private String fccAddress;
    private String fccPort;
    private String configurationPort;
    private String clientAddress;
    private String clientPort;

    public FccCommunicationCfgDTO() {
    }

    public String getFccAddress() {
        return fccAddress;
    }

    public void setFccAddress(String fccAddress) {
        this.fccAddress = fccAddress;
    }

    public String getFccPort() {
        return fccPort;
    }

    public void setFccPort(String fccPort) {
        this.fccPort = fccPort;
    }

    public String getConfigurationPort() {
        return configurationPort;
    }

    public void setConfigurationPort(String configurationPort) {
        this.configurationPort = configurationPort;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientPort() {
        return clientPort;
    }

    public void setClientPort(String clientPort) {
        this.clientPort = clientPort;
    }
}
