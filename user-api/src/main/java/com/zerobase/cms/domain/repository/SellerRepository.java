package com.zerobase.cms.domain.repository;

import com.zerobase.cms.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByEmailAndPasswordAndIsVerifyIsTrue(String email, String password);
    Optional<Seller> findByEmail(String email);
}
