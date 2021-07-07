package com.cts.mfpe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.mfpe.model.SpecialistDetail;

@Repository
public interface SpecialistDetailRepository extends JpaRepository<SpecialistDetail, Integer> {
  @Query("Select s from SpecialistDetail s where s.areaOfExpertise = ?1")
	List<SpecialistDetail> findByExpertise(AilmentCategory areaOfExpertise);

}
