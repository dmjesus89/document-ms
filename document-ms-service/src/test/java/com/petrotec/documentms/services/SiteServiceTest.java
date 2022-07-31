package com.petrotec.documentms.services;


import com.petrotec.service.exceptions.EntityAlreadyExistsException;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.repositories.SiteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SiteServiceTest {


    @InjectMocks
    private SiteService siteService;

    @Mock
    private SiteRepository siteRepository;

    @DisplayName("Validate site number uniqueness")
    @Test
    void validateUniqueSiteCode() {
        siteService.validateUniqueSiteCode(null);
        verify(siteRepository, never()).findByCode(any(String.class));

        when(siteRepository.findByCode("P025")).thenReturn(Optional.empty());

        siteService.validateUniqueSiteCode("P025");
        verify(siteRepository, times(1)).findByCode(any(String.class));

        when(siteRepository.findByCode("P026")).thenReturn(Optional.of(new Site()));
        EntityAlreadyExistsException t = assertThrows(EntityAlreadyExistsException.class, () -> siteService.validateUniqueSiteCode("P026"));
        assertEquals("site_ms_ws.site.site_code_already_exists",t.getSourceCode());
        verify(siteRepository, times(2)).findByCode(any(String.class));
    }

    @DisplayName("Validate site number uniqueness")
    @Test
    void validateUniqueSiteNumber() {
        siteService.validateUniqueSiteNumber(null);
        verify(siteRepository, never()).findBySiteNumber(any());

        when(siteRepository.findBySiteNumber("P025")).thenReturn(Optional.empty());

        siteService.validateUniqueSiteNumber("P025");
        verify(siteRepository, times(1)).findBySiteNumber(any());

        when(siteRepository.findBySiteNumber("P026")).thenReturn(Optional.of(new Site()));
        EntityAlreadyExistsException t = assertThrows(EntityAlreadyExistsException.class, () -> siteService.validateUniqueSiteNumber("P026"));
        assertEquals("site_ms_ws.site.site_number_already_exists",t.getSourceCode());
        verify(siteRepository, times(2)).findBySiteNumber(any());
    }

}