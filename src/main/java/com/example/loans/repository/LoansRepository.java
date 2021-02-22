package com.example.loans.repository;

import com.example.loans.domain.Loan;
import com.example.loans.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoansRepository extends JpaRepository<Loan, Long> {

    Page<Loan> findAll(Pageable pageable);

    Page<Loan> findAllByUser(User user, Pageable pageable);
}
