package com.petrotec.documentms.dtos.documents;

import io.micronaut.core.annotation.Introspected;

@Introspected
public enum SaleLineTypeEnum {
	FUEL_TRANSACTION,
	PREPAID_FUEL_TRANSACTION,
	PRODUCT,
	SPECIAL_PRODUCT,
}
