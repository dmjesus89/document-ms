package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class DocumentTypeDTO {

    private String code;
    private String prefix;
    private String description;
    private boolean isEnabled;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
