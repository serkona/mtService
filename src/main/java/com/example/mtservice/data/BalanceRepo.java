package com.example.mtservice.data;

import com.example.mtservice.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceRepo extends JpaRepository<Account, Long> {
    @Query("SELECT id FROM Account account")
    List<Long> findAllIds();
}
