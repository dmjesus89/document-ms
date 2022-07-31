package com.petrotec.documentms.interfaces;

public interface ICommunicationMethod {
    enum METHOD { IP_PROTOCOL, SERIAL_DATA }

    boolean isValid();
    METHOD getMethod();
}
