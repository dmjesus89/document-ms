package com.petrotec.documentms.services.configuration;

import com.petrotec.service.spec.SpecificationFilter;
import com.petrotec.documentms.entities.FuellingMode;
import com.petrotec.documentms.repositories.configuration.FuellingModeRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class FuellingModeServiceTest {

    @InjectMocks
    private IFuellingModeService fuellingModeService;
    @Mock
    private FuellingModeRepository fuellingModeRepository;
    @Mock
    private SpecificationFilter<FuellingMode> specificationFilter;


  /*  @DisplayName("Get All fuelling mode stored result empty")
    @Test
    void findAll_Empty() {
        when(specificationFilter.findAll(any(Filter.class), null, null)).thenReturn(Collections.singletonList(new FuellingMode()));
        PageResponse<FuellingModeDTO> result = fuellingModeService.findAll(null, null);
        verify(specificationFilter, times(1)).findAll(any(Filter.class), FuellingMode.class, any(PageAndSorting.class));
        verify(specificationFilter, times(1)).size(any(Filter.class), FuellingMode.class);
        assertEquals(PageResponse.empty(), result);
    }

    @DisplayName("Get All fuelling mode stored")
    @Test
    void getFuellingModeEmpty() {
        when(specificationFilter.findAll(any(Filter.class), FuellingMode.class, any(PageAndSorting.class))).thenReturn(Collections.singletonList(new FuellingMode()));
        PageResponse<FuellingModeDTO> result = fuellingModeService.findAll(any(PageAndSorting.class), any(Filter.class));
        verify(specificationFilter, times(1)).findAll(any(Filter.class), FuellingMode.class, any(PageAndSorting.class));
        verify(specificationFilter, times(1)).size(any(Filter.class), FuellingMode.class);
        assertEquals(1l, result.getSize());

    } */

}
