package com.g7.ercdataservice.repository;

import com.g7.ercdataservice.entity.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("DocumentRepository")
public interface DocumentRepository extends JpaRepository<Documents,Long> {
}
