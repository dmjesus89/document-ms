package com.petrotec.documentms.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrotec.service.exceptions.InvalidDataException;
import com.petrotec.documentms.interfaces.ICommunicationMethod;
import com.petrotec.documentms.dtos.communicationMethod.IpCommunicationMethod;
import com.petrotec.documentms.dtos.communicationMethod.SerialCommunicationMethod;
import com.petrotec.documentms.entities.CommunicationDevice;
import com.petrotec.documentms.repositories.*;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Map;


@Singleton
@Transactional
public class CommunicationMethodService {

    private final ObjectMapper objectMapper;
    private final CommunicationMethodRepository communicationMethodRepository;

    public CommunicationMethodService(ObjectMapper objectMapper, CommunicationMethodRepository communicationMethodRepository) {
        this.objectMapper = objectMapper;
        this.communicationMethodRepository = communicationMethodRepository;
    }

    /**
     * Sets the provided device with data regarding its communication method and details
     * @param device
     * @param commMethodTypeCode
     * @param communicationData
     */
    public void setDeviceCommunicationInterface(CommunicationDevice device, String commMethodTypeCode, Map<String, Object> communicationData){
        device.setCommunicationMethod(communicationMethodRepository.findByCode(commMethodTypeCode).orElseThrow(
                () -> new InvalidDataException("site_ms_ws.communication_method_service.communication_method_not_found", "Communication method with protocol code " + commMethodTypeCode + " not found.")
        ));

        if (!getDeviceCommunicationData(device).isValid()){
            throw new InvalidDataException("site_ms_ws.communication_method_service.invalid_communication_method_data", "Communication data is invalid. Check requirements");
        }
        device.setCommunicationMethodData(communicationData);
    }

    /**
     * Obtains the comunication already parsed and ready to be used
     * @param device
     * @return
     */
    public ICommunicationMethod getDeviceCommunicationData(CommunicationDevice device){
        switch (device.getCommunicationMethod().getCode()) {
            case "IP_PROTOCOL":
                TypeReference<IpCommunicationMethod> typeReference = new TypeReference<IpCommunicationMethod>(){};
                return objectMapper.convertValue(device.getCommunicationMethodData(), typeReference);

            case "SERIAL_CONNECTION":
                TypeReference<SerialCommunicationMethod> typeReference2 = new TypeReference<SerialCommunicationMethod>(){};
                return objectMapper.convertValue(device.getCommunicationMethodData(), typeReference2);
        }
        return null;
    }


}
