package com.g7.ercdataservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.g7.ercdataservice.enums.DecisionType;
import lombok.*;

import javax.persistence.*;
import java.net.URL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "evaluation_forms")
@ToString(exclude = {"version"})
public class EvaluationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "reviewer_id")
    private String reviewerId;

    @Column(name = "file_url")
    private URL url;

    private DecisionType decision;

    @ManyToOne
    @JoinColumn(name = "version_id")
    @JsonIgnore
    private Version version;

}
