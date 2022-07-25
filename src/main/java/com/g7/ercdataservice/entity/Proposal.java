package com.g7.ercdataservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.g7.ercdataservice.enums.ProposalStatus;
import com.g7.ercdataservice.enums.ProposalType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "proposal")
@NoArgsConstructor
@Getter
@Setter
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
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private Set<Version> versions ;

    @ManyToOne
    @JsonIgnore
    private User user;

}
