package com.mod5r.assesment.repositories;

import com.mod5r.assesment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
//    Optional<User> findByVerificationCode(String verificationCode);
}
