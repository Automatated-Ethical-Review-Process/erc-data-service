package com.g7.ercdataservice.repository;

import com.g7.ercdataservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Repository("UserInfoRepository")
public interface UserInfoRepository extends JpaRepository<User,String> {

    boolean existsUserInfoByIdOrEmail(@NotNull String id, @NotBlank @Size(max = 50) @Email(message = "Invalid Email") String email);

    boolean existsUserByEmail(@NotBlank @Size(max = 50) @Email(message = "Invalid Email") String email);

    @Query(nativeQuery = true,value = "SELECT user_id FROM public.user_roles WHERE role_id =:id")
    List<String> getAlla(@Param("id") int id);

    @Query(nativeQuery = true,value = "SELECT id, id_img, address, email, faculty, is_under_graduate, land, mobile, name,\n" +
            "nic, occupation, passport, \"position\", reg_num, university, year\n" +
            "\tFROM public.user_info WHERE id IN(SELECT user_id FROM public.user_roles WHERE role_id =:id)")
    List<User> getUsersByRole(@Param("id") int id);
}
