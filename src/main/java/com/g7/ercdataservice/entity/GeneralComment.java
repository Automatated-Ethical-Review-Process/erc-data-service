package com.g7.ercdataservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "general_comments")
public class GeneralComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private String content;

    @Column(nullable = false,updatable = false)
    private String reviewerId;

    @ManyToOne
    @JoinColumn(name = "version_id")
    @JsonIgnore
    private Version version;
}
