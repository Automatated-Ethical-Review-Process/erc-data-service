package com.g7.ercdataservice.repository;

import com.g7.ercdataservice.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("ProposalRepository")
public interface ProposalRepository extends JpaRepository<Proposal, UUID> {
}
