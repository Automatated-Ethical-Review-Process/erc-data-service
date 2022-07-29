package com.g7.ercdataservice.repository;

import com.g7.ercdataservice.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ApplicantRepository")
public interface ApplicantRepository extends JpaRepository<Applicant,String> {

}
