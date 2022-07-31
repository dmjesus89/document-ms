package com.petrotec.documentms.entities;

import java.util.Map;

/**
 * Representes a Device that has communication
 */
public interface CommunicationDevice {

    CommunicationMethod getCommunicationMethod();

    void setCommunicationMethod(CommunicationMethod communicationMethod);

    Map<String, Object> getCommunicationMethodData();

    void setCommunicationMethodData(Map<String, Object> communicationMethodData);
}
