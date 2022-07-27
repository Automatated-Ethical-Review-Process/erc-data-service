package com.g7.ercdataservice.repository;

import com.g7.ercdataservice.entity.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ReviewerRepository")
public interface ReviewerRepository extends JpaRepository<Reviewer,String> {
}
