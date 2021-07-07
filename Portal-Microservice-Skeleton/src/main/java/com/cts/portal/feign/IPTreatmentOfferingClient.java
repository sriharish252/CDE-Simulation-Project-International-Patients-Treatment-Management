package com.cts.portal.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.portal.exception.AuthorizationException;
import com.cts.portal.exception.IPTreatmentPackageNotFoundException;
import com.cts.portal.model.AilmentCategory;
import com.cts.portal.model.IPTreatmentPackage;
import com.cts.portal.model.SpecialistDetail;

import io.swagger.annotations.ApiParam;

@FeignClient(name = "IPTreatmentOffering-service", url = "${ipoffering.URL}")
public interface IPTreatmentOfferingClient {
    
	@GetMapping("/ipTreatmentPackages")
	public List<IPTreatmentPackage> getAllIPTreatmentPackage(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException;
	
	@GetMapping("/ipTreatmentPackageByName/{ailment}/{packageName}")
	public IPTreatmentPackage getIPTreatmentPackageByName(
			@ApiParam(name = "ailment", value = "ailment of the package") @PathVariable AilmentCategory ailment,
			@ApiParam(name = "packageName", value = "name of the package") @PathVariable String packageName,
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws AuthorizationException, IPTreatmentPackageNotFoundException;
	
	@GetMapping("/specialists")
	public List<SpecialistDetail> getAllSpecialist(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) throws AuthorizationException;
	@GetMapping("/specialistsByExpertsise/{areaOfExpertise}")
	public List<SpecialistDetail> getAllSpecialistsByExpertise(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,@PathVariable AilmentCategory areaOfExpertise) throws AuthorizationException;
	
	///////////////////////////////////////////////////////////////////////////////////
	
	@DeleteMapping("/deleteSpecialist/{specialistId}")
	public ResponseEntity<String> deleteSpecialist(@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable(value="specialistId") int specialistId) throws AuthorizationException;
	
	//----------------------------------------------------------------------
	@PostMapping("/addSpecialist")
	public ResponseEntity<String> addSpecialist(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody SpecialistDetail specialistDetail);
	
	//*************************************
	
	@PutMapping("/updatePackage/{pid}/{treatmentPackageName}")
	public ResponseEntity<String> updatePackage(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader, @PathVariable int pid, @PathVariable String treatmentPackageName) throws AuthorizationException;
}
