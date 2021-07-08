package com.cts.mfpe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.mfpe.model.PackageDetail;

@Repository
public interface PackageDetailRepository extends JpaRepository<PackageDetail, Integer> {
        //**********
        @Modifying
	    @Query("update PackageDetail SET treatmentPackageName=:treatmentPackageName WHERE pid=:pid")
	    void updateById(@Param("pid") int pid, @Param("treatmentPackageName") String treatmentPackageName);
        
        /*
	    @Query("select count(pid) from packages where pid=:pid")
	    int countByIdFromDb(@Param("pid") int pid);
	    */
	    
}
