package com.g7.ercdataservice.repository;

import com.g7.ercdataservice.entity.EvaluationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationFormRepository extends JpaRepository<EvaluationForm,Long> {

}
