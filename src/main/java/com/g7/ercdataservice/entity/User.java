package com.g7.ercdataservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
@Table(
        name = "user_info",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email","mobile"})
        }
)
public class User {

    @Id
    @Column(name = "id",updatable = false,nullable = false)
    @NotNull
    private String id ;

    @NotBlank
    @Size(max=50)
    @Email(message = "Invalid Email")
    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "name",nullable = false,updatable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @Size(min = 4)
    @NotNull
    private String address;

    //@Pattern(regexp = "(^[0-9]{10}$)",message = "Invalid land number")
    @Size(max = 10)
    @Column(name = "land")
    private String landNumber;

    @Pattern(regexp = "(^[0-9]{10}$)",message = "Invalid mobile number")
    @Size(max = 10)
    @Column(name = "mobile",nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    @NotNull
    private Boolean isUnderGraduate;

    private String nic;

    private String passport;

    @Column(nullable = false)
    @NotNull
    private URL IdImg;

    private String occupation;

    private String position;

    private String university;

    private String faculty;

    private String year;

    @Column(name = "reg_num",updatable = false)
    private String registrationNumber;

    @Column(columnDefinition = "TEXT")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> educationalQualifications;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles =  new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<Proposal> proposals;

//    @ManyToMany(mappedBy = "reviewers")
//    @JsonIgnore
//    private Set<Version> versions;

}
