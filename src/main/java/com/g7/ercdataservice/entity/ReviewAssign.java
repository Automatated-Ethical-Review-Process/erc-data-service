package com.g7.ercdataservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.g7.ercdataservice.enums.ReviewerStatus;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "review_assign")
@Builder
@ToString(exclude = {"proposal", "version"})
public class ReviewAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private ReviewerStatus status;

    @ManyToOne
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id")
    //@JsonIgnore
    private Reviewer reviewer;

    @ManyToOne
    @JoinColumn(name = "version_id", referencedColumnName = "id")
    @JsonIgnore
    private Version version;

    @ManyToOne
    @JoinColumn(name = "proposal_id", referencedColumnName = "id")
    @JsonIgnore
    private Proposal proposal;
}
