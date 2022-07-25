package com.g7.ercdataservice.repository;

import com.g7.ercdataservice.entity.Proposal;
import com.g7.ercdataservice.entity.User;
import com.g7.ercdataservice.enums.ProposalStatus;
import com.g7.ercdataservice.enums.ProposalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("ProposalRepository")
public interface ProposalRepository extends JpaRepository<Proposal, UUID> {

    List<Proposal> findProposalByUser(User user);
    long countProposalsByStatusAndUser(ProposalStatus status, User user);

    Optional<Proposal> findProposalByUserAndId(User user, UUID id);
}
