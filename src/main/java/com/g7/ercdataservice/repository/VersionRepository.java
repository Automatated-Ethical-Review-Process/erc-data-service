package com.g7.ercdataservice.repository;

import com.g7.ercdataservice.entity.UserInfo;
import com.g7.ercdataservice.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository("VersionRepository")
public interface VersionRepository extends JpaRepository<Version,Long> {

    Optional<Version> findVersionByReviewers(Set<UserInfo> reviewers);
}
