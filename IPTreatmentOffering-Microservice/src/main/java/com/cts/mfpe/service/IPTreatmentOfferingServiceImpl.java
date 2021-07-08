package com.cts.mfpe.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.cts.mfpe.exception.IPTreatmentPackageNotFoundException;
import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.IPTreatmentPackage;
import com.cts.mfpe.model.SpecialistDetail;
import com.cts.mfpe.repository.IPTreatmentPackageRepository;
import com.cts.mfpe.repository.PackageDetailRepository;
import com.cts.mfpe.repository.SpecialistDetailRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IPTreatmentOfferingServiceImpl implements IPTreatmentOfferingService {

	@Autowired
	IPTreatmentPackageRepository treatmentPackRepository;

	@Autowired
	SpecialistDetailRepository specialistRepository;
	
	@Autowired
	PackageDetailRepository packageDetailRepository;

	@Override
	public List<IPTreatmentPackage> findAllIPTreatmentPackages() {

		List<IPTreatmentPackage> treatmentPackages = treatmentPackRepository.findAll();
		log.info("[IPTreatmentPackage details:] "+ treatmentPackages);
		return treatmentPackages;
	}

	@Override
	public IPTreatmentPackage findIPTreatmentPackageByNameOnlyOne(AilmentCategory ailment, String packageName) throws IPTreatmentPackageNotFoundException {

		IPTreatmentPackage treatmentPackage = treatmentPackRepository.findByName(ailment, packageName).get(0);
		System.out.println(treatmentPackage);
		
		log.info("[IPTreatmentPackage ("+packageName+") detail:] "+ treatmentPackage);
		return treatmentPackage;
	}
	
	@Override
	public List<IPTreatmentPackage> findIPTreatmentPackageByName(AilmentCategory ailment, String packageName) throws IPTreatmentPackageNotFoundException {

		List<IPTreatmentPackage> treatmentPackage = treatmentPackRepository.findByName(ailment, packageName);
		System.out.println(treatmentPackage);
		
		log.info("[IPTreatmentPackage ("+packageName+") detail:] "+ treatmentPackage);
		return treatmentPackage;
	}

	@Override
	public List<SpecialistDetail> findAllSpecialists() {

		List<SpecialistDetail> specialists = specialistRepository.findAll();
		log.info("[Specialist details:] " + specialists);
		return specialists;
	}
	//SpecialistByExpertise
	@Override
	public List<SpecialistDetail> findAllSpecialistsByExpertise(AilmentCategory areaOfExpertise) {
		List<SpecialistDetail> specialists = specialistRepository.findByExpertise(areaOfExpertise);
		log.info("[Specialist details:] " + specialists);
		return specialists;
	}
	
	
	///////////////////////////////////////
	
	@Override
	public void deleteSpecialist(int specialistId) {
		specialistRepository.deleteById(specialistId);
		log.info("[Specialist deleted successfully] ");
	}
	
	//------------------------------------
	
	@Override
	public boolean addSpecialist(SpecialistDetail specialistDetail) {	// add specialist details to the specialist repository
		specialistRepository.save(specialistDetail);
		return true;
	}

	public long getSpecialistsCount() {
		return specialistRepository.count();
	}
	
	@Override
	public SpecialistDetail findSpecialistById(int id) {
		return specialistRepository.findById(id).get();
	}
	
	//*****************************************
	//implemented method  
	@Override
	@Transactional
	public void updateTreatmentPackage(int pid, String treatmentPackageName) {
		// TODO Auto-generated method stub
		System.out.println("Updating using pid,tpname");
		//redirecting to repo
		packageDetailRepository.updateById(pid, treatmentPackageName);
		System.out.println("Updated!!!---");
		log.info("[Package updated successfully] ");
	}

	
	@Override
	@Transactional
	public boolean countById(int pid) {
		System.out.println("Check the Pid in Db, pid: " + pid);
		return (!packageDetailRepository.findById(pid).isEmpty());
	}
	
	
}
