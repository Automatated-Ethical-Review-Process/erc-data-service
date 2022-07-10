package com.g7.ercdataservice.repository;

import com.g7.ercdataservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Repository("UserInfoRepository")
public interface UserInfoRepository extends JpaRepository<UserInfo,String> {

    boolean existsUserInfoByIdOrEmail(@NotNull String id, @NotBlank @Size(max = 50) @Email(message = "Invalid Email") String email);
}
