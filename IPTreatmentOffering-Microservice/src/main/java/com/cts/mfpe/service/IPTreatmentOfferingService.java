package com.cts.mfpe.service;

import java.util.List;

import com.cts.mfpe.exception.IPTreatmentPackageNotFoundException;
import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.IPTreatmentPackage;
import com.cts.mfpe.model.SpecialistDetail;

public interface IPTreatmentOfferingService {
	
	List<IPTreatmentPackage> findAllIPTreatmentPackages();
	IPTreatmentPackage findIPTreatmentPackageByName(AilmentCategory ailment, String packageName) throws IPTreatmentPackageNotFoundException;
	List<SpecialistDetail> findAllSpecialists();
	//specialistByExpertise
	List<SpecialistDetail> findAllSpecialistsByExpertise(AilmentCategory ailment) throws IPTreatmentPackageNotFoundException;

	
	////////////////////////////////////////////
	
	void deleteSpecialist(int specialistId);
	
	//------------------------------------------
	
	boolean addSpecialist(SpecialistDetail specialistDetail);
	long getSpecialistsCount();
	SpecialistDetail findSpecialistById(int id);
	
	//************************
	//updatetreatmentpackage
	void updateTreatmentPackage(int pid, String treatmentPackageName);
	
}
