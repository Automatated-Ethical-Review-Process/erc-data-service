package com.g7.ercdataservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.g7.ercdataservice.enums.VersionStatus;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "version")
@ToString(exclude = {"documents","evaluationForms"})
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private int number;

    @Enumerated(EnumType.STRING)
    private VersionStatus status;

    @Column(columnDefinition="TEXT")
    private String comment;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "proposal_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private Proposal proposal;

    @OneToMany(
            mappedBy = "version",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Documents> documents;

    @OneToMany(
            mappedBy = "version",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<EvaluationForm> evaluationForms ;

    @OneToMany(
            mappedBy = "version",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<GeneralComment> generalComments;

}
