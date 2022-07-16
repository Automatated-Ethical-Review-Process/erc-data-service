package com.g7.ercdataservice.repository;

import com.g7.ercdataservice.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("VersionRepository")
public interface VersionRepository extends JpaRepository<Version,Long> {

}
