package com.petrotec;

import io.micronaut.context.annotation.Import;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info = @Info(title = "Document Microservice Interface definition", version = "2.17.0"))
@Import(packages = {"com.petrotec.queue.domain", "com.petrotec.documentms.entities", "com.petrotec.categories","com.petrotec.service.config"},  annotated = "*")
public class DocumentApplication {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(DocumentApplication.class);
        Micronaut.run(DocumentApplication.class, args);
    }

}

