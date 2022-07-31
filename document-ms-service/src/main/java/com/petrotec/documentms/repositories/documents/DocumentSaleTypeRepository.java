package com.petrotec.documentms.repositories.documents;

import com.petrotec.documentms.entities.documents.DocumentSaleType;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public abstract class DocumentSaleTypeRepository implements CrudRepository<DocumentSaleType, Integer> {

    public abstract Optional<DocumentSaleType> findByCode(@NotBlank String code);

}
