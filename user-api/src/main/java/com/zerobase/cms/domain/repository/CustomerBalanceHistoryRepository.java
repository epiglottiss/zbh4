package com.zerobase.cms.domain.repository;

import com.zerobase.cms.domain.model.CustomerBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface CustomerBalanceHistoryRepository extends JpaRepository<CustomerBalanceHistory, Long> {
    Optional<CustomerBalanceHistory> findFirstByCustomer_IdOrderByIdDesc(@RequestParam("cutomer_id") Long id);

}
