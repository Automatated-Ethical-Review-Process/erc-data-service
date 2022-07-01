package com.g7.ercdataservice.entity;

import com.g7.ercdataservice.enums.ProposalStatus;
import com.g7.ercdataservice.enums.ProposalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "proposal")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Proposal {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ProposalType type;

    private Instant date = Instant.now();

    @Enumerated(EnumType.STRING)
    private ProposalStatus status;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "proposal_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Set<Version> versions ;

}
