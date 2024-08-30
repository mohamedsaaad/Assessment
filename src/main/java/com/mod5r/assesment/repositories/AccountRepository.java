package com.mod5r.assesment.repositories;

import com.mod5r.assesment.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<
        Account,Long> {
    Optional<Account> findByIban(String iban);
}
