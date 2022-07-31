package com.petrotec.documentms.entities;

import io.micronaut.core.annotation.Introspected;

import java.time.LocalDateTime;

@Introspected
public interface FlatNotifiableElementContact {
    Long getId();

    /*String getCode();*/

    String getLocaleCode();

    String getNotifiableMethodCode();

    String getAlarmSeverityCode();

    String getContact();

    LocalDateTime getCreatedOn();

    LocalDateTime getUpdatedOn();

}
