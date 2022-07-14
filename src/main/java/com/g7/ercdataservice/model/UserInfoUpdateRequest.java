package com.g7.ercdataservice.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.net.URL;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserInfoUpdateRequest {

    @Size(min = 4)
    @NotNull
    private String address;
    @Pattern(regexp = "(^[0-9]{10}$)",message = "Invalid land number")
    @Size(max = 10)
    private String landNumber;
    @Pattern(regexp = "(^[0-9]{10}$)",message = "Invalid mobile number")
    @Size(max = 10)
    @NotNull
    private String mobileNumber;
    @NotNull
    private Boolean isUnderGraduate;
    private String nic;
    private String passport;
    @NotNull
    private URL IdImg;
    private String occupation;
    private String possition;
    private String university;
    private String faculty;
    private String year;
    private List<String> educationalQualifications;
}
