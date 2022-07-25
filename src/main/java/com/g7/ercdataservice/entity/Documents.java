package com.g7.ercdataservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.g7.ercdataservice.enums.DocumentType;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "documents")
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private DocumentType type;
    @Column(name = "file_name")
    private String file;
    @ManyToOne
    @JoinColumn(name = "version_id")
    @JsonIgnore
    private Version version;
    public Documents(DocumentType type, String file, Version version) {
        this.type = type;
        this.file = file;
        this.version = version;
    }
}
