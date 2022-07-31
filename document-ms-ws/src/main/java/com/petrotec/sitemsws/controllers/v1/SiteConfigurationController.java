package com.petrotec.sitemsws.controllers.v1;

import com.petrotec.api.BaseResponse;
import com.petrotec.documentms.dtos.siteConfiguration.SiteConfigurationExtendedDTO;
import com.petrotec.documentms.dtos.siteConfiguration.v2.SiteConfigurationDTO;
import com.petrotec.documentms.services.SiteConfigurationService;
import com.petrotec.documentms.services.SiteConfigurationServiceV2;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.micronaut.http.HttpStatus.OK;

@Controller(value = "/api/v1/siteConfiguration") @Tag(name = "Site Configuration") public class SiteConfigurationController {
	private static final Logger LOG = LoggerFactory.getLogger(SiteConfigurationController.class);

	private final SiteConfigurationService siteConfigurationService;
	private final SiteConfigurationServiceV2 siteConfigurationServiceV2;

	public SiteConfigurationController(SiteConfigurationService siteConfigurationService, SiteConfigurationServiceV2 siteConfigurationServiceV2) {
		this.siteConfigurationService = siteConfigurationService;
		this.siteConfigurationServiceV2 = siteConfigurationServiceV2;
	}

	/**
	 * Returns the existing configuration for a site
	 *
	 * @param siteCode
	 * @return
	 */
	@Get("/{siteCode}")
	@ApiResponse(responseCode = "200", description = "Get Site configuration to configure Petrotec FCC")
	public BaseResponse<SiteConfigurationExtendedDTO> getSiteConfiguration(
			String siteCode) {
		LOG.debug("Handling request for Site configuration.");
		SiteConfigurationExtendedDTO res = siteConfigurationService.getSiteConfiguration(siteCode);

		return BaseResponse.success(OK.getReason(), OK.getCode(), "Site Configuration", res);
	}

	/**
	 * Returns the existing configuration for a site
	 *
	 * @param siteNumber siteNumber as site identifier
	 * @return
	 */
	@Get("/bySiteNumber/{siteNumber}")
	@ApiResponse(responseCode = "200", description = "Get Site configuration to configure Petrotec POS and FCC")
	public BaseResponse<SiteConfigurationDTO> getSiteConfigurationBySiteNumber(
			String siteNumber) {
		LOG.debug("Handling request for Site configuration.");
		return BaseResponse.success(OK.getReason(), OK.getCode(), "Site Configuration", siteConfigurationServiceV2.getSiteConfiguration(siteNumber));
	}
}
