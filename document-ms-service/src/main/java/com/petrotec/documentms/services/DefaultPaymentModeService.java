package com.petrotec.documentms.services;

import com.petrotec.api.BaseResponse;
import com.petrotec.api.PCSConstants;
import com.petrotec.api.PageAndSorting;
import com.petrotec.api.PageResponse;
import com.petrotec.api.filter.Filter;
import com.petrotec.service.exceptions.EntityNotFoundException;
import com.petrotec.service.exceptions.InvalidDataException;
import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.service.spec.SpecificationFilter;
import com.petrotec.service.util.HttpUtilities;
import com.petrotec.documentms.dtos.documents.PaymentModeDTO;
import com.petrotec.documentms.dtos.siteDevice.SimpleSitePaymentModeDTO;
import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.documents.DocumentType;
import com.petrotec.documentms.entities.documents.PaymentMode;
import com.petrotec.documentms.entities.documents.SitePaymentMode;
import com.petrotec.documentms.interfaces.PaymentModeService;
import com.petrotec.documentms.mappers.PaymentModeMapper;
import com.petrotec.documentms.repositories.SitePaymentModeRepository;
import com.petrotec.documentms.repositories.SiteRepository;
import com.petrotec.documentms.repositories.documents.DocumentTypeRepository;
import com.petrotec.documentms.repositories.interfaces.PaymentModeRepository;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static io.micronaut.http.HttpStatus.BAD_REQUEST;
import static io.micronaut.http.HttpStatus.OK;

@Singleton
@Transactional
public class DefaultPaymentModeService implements PaymentModeService {

    public static final String PAYMENT_MODE_SITE_CODE = "payment_mode.site_code";
    public static final String SITE_CODE_NOT_FOUND = "Site code not found.";
    private static final Logger LOG = LoggerFactory.getLogger(DefaultPaymentModeService.class);
    private final SpecificationFilter<PaymentMode> specificationFilter;
    private final PaymentModeMapper paymentModeMapper;
    private final PaymentModeRepository paymentModeRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final SiteRepository siteRepository;
    private final SitePaymentModeRepository sitePaymentModeRepository;
    private final TranslateMapper translateMapper;

    public DefaultPaymentModeService(SpecificationFilter<PaymentMode> specificationFilter, PaymentModeMapper paymentModeMapper,
                                     PaymentModeRepository paymentModeRepository, DocumentTypeRepository documentTypeRepository, SiteRepository siteRepository, SitePaymentModeRepository sitePaymentModeRepository, TranslateMapper translateMapper) {
        this.specificationFilter = specificationFilter;
        this.paymentModeMapper = paymentModeMapper;
        this.paymentModeRepository = paymentModeRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.siteRepository = siteRepository;
        this.sitePaymentModeRepository = sitePaymentModeRepository;
        this.translateMapper = translateMapper;
    }

    @Override
    public PageResponse<PaymentModeDTO> findAll(@Nullable PageAndSorting pageAndSorting,
                                                @Nullable Filter filterQuery) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        return PageResponse.from(
                specificationFilter.findAll(filterQuery, PaymentMode.class, pageAndSorting).stream().map(p -> paymentModeMapper.toDTO(p, locale))
                        .collect(Collectors.toList()), pageAndSorting,
                () -> specificationFilter.size(filterQuery, PaymentMode.class));
    }

    @Override
    public PaymentModeDTO get(String code) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        return paymentModeMapper.toDTO(getEntityByCode(code), locale);
    }

    @Override
    public PaymentModeDTO create(PaymentModeDTO paymentModeDTO) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        if (Objects.isNull(paymentModeDTO.getCode())) {
            paymentModeDTO.setCode(UUID.randomUUID().toString());
        }

        validateReferencePaymentCode(paymentModeDTO);

        PaymentMode paymentMode = paymentModeMapper.fromDTO(paymentModeDTO, locale);
        paymentMode.setDocumentType(getDocumentType(paymentModeDTO.getDocumentTypeCode()));
        if (Objects.nonNull(paymentModeDTO.getReferencePaymentModeCode())) {
            paymentMode.setPaymentModeReference(getEntityByCode(paymentModeDTO.getReferencePaymentModeCode()));
        }
        PaymentMode paymentModeSave = paymentModeRepository.save(paymentMode);
        return paymentModeMapper.toDTO(paymentModeSave, locale);
    }

    @Override
    public PaymentModeDTO update(String code, PaymentModeDTO paymentModeDTO) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);

        validateReferencePaymentCode(paymentModeDTO);

        PaymentMode paymentUpdate = paymentModeMapper.fromCreate(paymentModeDTO, getEntityByCode(code), locale);
        paymentUpdate.setDocumentType(getDocumentType(paymentModeDTO.getDocumentTypeCode()));
        if (Objects.nonNull(paymentModeDTO.getReferencePaymentModeCode())) {
            paymentUpdate.setPaymentModeReference(getEntityByCode(paymentModeDTO.getReferencePaymentModeCode()));
        }
        return paymentModeMapper.toDTO(paymentModeRepository.update(paymentUpdate), locale);
    }

    @Override
    public SimpleSitePaymentModeDTO createSitePaymentMode(String siteCode, String paymentCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        Site site = siteRepository.findByCodeAndEnabled(siteCode)
                .orElseThrow(() -> new EntityNotFoundException(PAYMENT_MODE_SITE_CODE, SITE_CODE_NOT_FOUND));
        PaymentMode paymentMode = paymentModeRepository.findByCode(paymentCode)
                .orElseThrow(() -> new EntityNotFoundException("payment_mode.payment_mode_code", " Payment mode code not found."));
        SitePaymentMode sitePaymentMode = new SitePaymentMode();
        sitePaymentMode.setPaymentMode(paymentMode);
        sitePaymentMode.setSite(site);


        try {
            sitePaymentModeRepository.save(sitePaymentMode);
        } catch (InvalidDataException e) {
            throw new InvalidDataException("site_payment_mode.error_to_save_site_payment_mode", "Error to save site payment mode.");
        }

        SimpleSitePaymentModeDTO dto = new SimpleSitePaymentModeDTO();
        dto.setSiteCode(site.getCode());
        dto.setSiteDescription(translateMapper.translatedDescription(site.getDescription(), locale));
        dto.setCode(paymentMode.getCode());
        dto.setDescription(translateMapper.translatedDescription(paymentMode.getDescription(), locale));

        return dto;
    }

    @Override
    public List<SimpleSitePaymentModeDTO> findBySiteCode(String siteCode) {
        String locale = HttpUtilities.getHeaderOrThrow(PCSConstants.ATTR_LOCALE);
        List<SimpleSitePaymentModeDTO> simpleSitePaymentModeDTOList = new ArrayList<>();
        Site site = siteRepository.findByCodeAndEnabled(siteCode)
                .orElseThrow(() -> new EntityNotFoundException(PAYMENT_MODE_SITE_CODE, SITE_CODE_NOT_FOUND));
        List<SitePaymentMode> sitePaymentMode = sitePaymentModeRepository.findBySite(site);

        sitePaymentMode.forEach(p -> {
            PaymentMode paymentMode = paymentModeRepository.findById(p.getPaymentMode().getId()).orElseThrow(() -> new EntityNotFoundException("payment_mode.payment_mode_code", " Payment mode code not found."));
            SimpleSitePaymentModeDTO dto = new SimpleSitePaymentModeDTO();
            dto.setSiteCode(p.getSite().getCode());
            dto.setSiteDescription(translateMapper.translatedDescription(p.getSite().getDescription(), locale));
            dto.setCode(paymentMode.getCode());
            dto.setDescription(translateMapper.translatedDescription(paymentMode.getDescription(), locale));
            simpleSitePaymentModeDTOList.add(dto);
        });

        return simpleSitePaymentModeDTOList;
    }

    @Override
    public BaseResponse<Void> deleteSitePaymentMode(String siteCode) {
        Site site = siteRepository.findByCodeAndEnabled(siteCode)
                .orElseThrow(() -> new EntityNotFoundException(PAYMENT_MODE_SITE_CODE, SITE_CODE_NOT_FOUND));
        try {
            sitePaymentModeRepository.deleteAll(sitePaymentModeRepository.findBySite(site));
        } catch (InvalidDataException e) {
            return BaseResponse.error(BAD_REQUEST.getReason(), BAD_REQUEST.getCode(),
                    "Error to delete site payment mode.", null);
        }
        return BaseResponse.success(OK.getReason(), OK.getCode(), "Payment mode deleted by site with success.", null);
    }

    private DocumentType getDocumentType(String documentTypeCode) {
        return documentTypeRepository.findByCode(documentTypeCode).orElseThrow(
                () -> new javax.persistence.EntityNotFoundException(
                        String.format("Error while parsing documentDTO. Could not find any document with code: [%d]", documentTypeCode)));
    }

    private void validateReferencePaymentCode(PaymentModeDTO paymentModeDTO) {
        if (Objects.nonNull(paymentModeDTO.getReferencePaymentModeCode())) {
            PaymentMode entityByCode = getEntityByCode(paymentModeDTO.getReferencePaymentModeCode());
            paymentModeRepository.findByPaymentModeReference(entityByCode.getId()).ifPresent(p -> {
                        throw new InvalidDataException("payment_mode.reference_code_already_associated", "Reference payment mode already is associated");
                    }
            );
        }
    }

    private PaymentMode getEntityByCode(String code) {
        Optional<PaymentMode> paymentModeCode = paymentModeRepository.findByCode(code);
        if (!paymentModeCode.isPresent()) {
            LOG.warn("Payment mode with code {} not found", code);
            throw new EntityNotFoundException("payment_mode.code_not_found", "Payment mode code not found");
        }
        return paymentModeCode.get();
    }
}
