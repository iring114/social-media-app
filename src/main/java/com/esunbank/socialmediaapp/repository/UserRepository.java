package com.esunbank.socialmediaapp.repository;

import com.esunbank.socialmediaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "CALL sp_get_user_by_phone(:phoneNumber)", nativeQuery = true)
    Optional<User> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    Optional<User> findByEmail(String email);

    @Query(value = "CALL sp_register_user(:userName, :email, :phoneNumber, :password, :salt, :biography)", nativeQuery = true)
    Long registerUser(
            @Param("userName") String userName,
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber,
            @Param("password") String password,
            @Param("salt") String salt,
            @Param("biography") String biography);
}