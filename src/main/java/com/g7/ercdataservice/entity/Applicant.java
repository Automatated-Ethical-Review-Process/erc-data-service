package com.g7.ercdataservice.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "applicant")
@Builder
public class Applicant {

    @Id
    @NotBlank
    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String id;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(name = "applicant_proposal",joinColumns = @JoinColumn(name ="applicant_id"),
            inverseJoinColumns = @JoinColumn(name = "proposal_id"))
    private Set<Proposal> proposals =  new HashSet<>();
}
