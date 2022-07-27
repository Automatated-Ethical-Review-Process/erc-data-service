package com.g7.ercdataservice.model;

import com.g7.ercdataservice.entity.Role;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRoleUpdateRequest {
    private String id;
    private Set<Role> roles;
}
