package com.petrotec.documentms.services.interfaces;

import com.petrotec.documentms.dtos.FuelPointResponseDTO;
import com.petrotec.documentms.dtos.RefuelDTO;

/**
 * This is the interface for the local fuelPoint operations controller (APLPMP).
 */
public interface APLPMPService {

	boolean isFuelPointReserved(String site, String device);

	FuelPointResponseDTO fuelPointReserve(String posTransactionUUID, String site, String device ) throws Exception;

	FuelPointResponseDTO fuelPointUnreserve(String posTransactionUUID, String site, String device) throws Exception;

	FuelPointResponseDTO fuelPointRefuel(String posTransactionUUID, String site, String device, RefuelDTO refuelDTO) throws Exception;

/*	FuelPointResponseDTO fuelPointRefuelFinished(String posTransactionUUID, String site, String device, RefuelDTO refuelDTO) throws Exception; */
}
