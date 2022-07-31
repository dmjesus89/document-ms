package com.petrotec.documentms.mappers;

import com.petrotec.service.mappers.TranslateMapper;
import com.petrotec.documentms.dtos.documents.PaymentModeDTO;
import com.petrotec.documentms.dtos.documents.ReferencePaymentModeDTO;
import com.petrotec.documentms.entities.documents.PaymentMode;
import com.petrotec.documentms.mappers.documents.DocumentMapper;
import org.mapstruct.Context;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

@Singleton
public class PaymentModeMapper {

	private final TranslateMapper translateMapper;
	private final DocumentMapper documentMapper;

	public PaymentModeMapper(TranslateMapper translateMapper, DocumentMapper documentMapper) {
		this.translateMapper = translateMapper;
		this.documentMapper = documentMapper;
	}

	public PaymentModeDTO toDTO(PaymentMode entity, @Context String locale) {
		PaymentModeDTO dto = new PaymentModeDTO();
		ReferencePaymentModeDTO referencePaymentModeDTO = new ReferencePaymentModeDTO();
		dto.setCode(entity.getCode());
		dto.setDescription(translateMapper.translatedDescription(entity.getDescription(), locale));
		dto.setDocumentType(documentMapper.toDTO(entity.getDocumentType(), locale));
		dto.setEnabled(entity.isEnabled());
		referencePaymentModeDTO.setCode(entity.getPaymentModeReference().getCode());
		referencePaymentModeDTO.setDescription(translateMapper.translatedDescription(entity.getPaymentModeReference().getDescription(), locale));
		dto.setReferencePaymentModeDTO(referencePaymentModeDTO);
		dto.setOpenCashDrawer(entity.isOpenCashDrawer());
		dto.setOrderNo(entity.getOrderNo());
		dto.setPrepaymentAllowed(entity.isPrepaymentAllowed());
		dto.setRefundAllowed(entity.isRefundAllowed());
		dto.setChangeAllowed(entity.isChangeAllowed());
		dto.setCancelAllowed(entity.isCancelAllowed());
		dto.setPrinterAllowed(entity.isPrinterAllowed());
		dto.setPayEntireTransaction(entity.isPayEntireTransaction());
		dto.setReceiptCopies(entity.getReceiptCopies());
		dto.setAutoReceipt(entity.isAutoReceipt());
		return dto;
	}

	public PaymentMode fromDTO(PaymentModeDTO dto, @Context String locale) {
		PaymentMode paymentMode = new PaymentMode();
		return getPaymentMode(dto, paymentMode, locale);
	}

	public PaymentMode fromCreate(PaymentModeDTO dto, PaymentMode paymentMode, String locale) {
		if (dto == null && paymentMode == null) {
			return null;
		}

		return getPaymentMode(dto, paymentMode, locale);
	}

	@NotNull
	private PaymentMode getPaymentMode(PaymentModeDTO dto, PaymentMode paymentMode, String locale) {
		paymentMode.setCode(dto.getCode());
		paymentMode.setDescription(translateMapper.setDescription(dto.getDescription(), null, locale));
		paymentMode.setEnabled(dto.isEnabled());
		paymentMode.setOpenCashDrawer(dto.isOpenCashDrawer());
		paymentMode.setOrderNo(dto.getOrderNo());
		paymentMode.setPrepaymentAllowed(dto.isPrepaymentAllowed());
		paymentMode.setRefundAllowed(dto.isRefundAllowed());
		paymentMode.setChangeAllowed(dto.isChangeAllowed());
		paymentMode.setCancelAllowed(dto.isCancelAllowed());
		paymentMode.setPrinterAllowed(dto.isPrinterAllowed());
		paymentMode.setPayEntireTransaction(dto.isPayEntireTransaction());
		paymentMode.setReceiptCopies(dto.getReceiptCopies());
		paymentMode.setAutoReceipt(dto.isAutoReceipt());

		return paymentMode;
	}
}
