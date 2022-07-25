package com.g7.ercdataservice.repository;

import com.g7.ercdataservice.entity.Role;
import com.g7.ercdataservice.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("RoleRepository")
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findRoleByName(ERole name);
}
