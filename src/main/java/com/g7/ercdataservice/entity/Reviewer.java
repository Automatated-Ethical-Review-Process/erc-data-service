package com.g7.ercdataservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.g7.ercdataservice.enums.ERole;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "reviewer")
@Builder
public class Reviewer {

    @Id
    @NotEmpty
    @NotNull
    @NotBlank
    private String id;
    @Enumerated(EnumType.STRING)
    private ERole role;
    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "reviewer"
    )
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<ReviewAssign> reviewAssigns ;
}
