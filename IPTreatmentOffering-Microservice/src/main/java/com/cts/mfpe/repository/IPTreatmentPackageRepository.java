package com.cts.mfpe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.mfpe.model.AilmentCategory;
import com.cts.mfpe.model.IPTreatmentPackage;

@Repository
public interface IPTreatmentPackageRepository  extends JpaRepository<IPTreatmentPackage, Integer> {

	@Query("Select p from IPTreatmentPackage p where p.ailmentCategory = ?1 and p.packageDetail.treatmentPackageName = ?2")
	List<IPTreatmentPackage> findByName(AilmentCategory ailment, String packageName);

//	@Query("Select p from IPTreatmentPackage p where p.ailmentCategory = ?1 and p.packageDetail.treatmentPackageName = ?2 ")
//	IPTreatmentPackage findByNameOnlyOne(AilmentCategory ailment, String packageName);
	
}
