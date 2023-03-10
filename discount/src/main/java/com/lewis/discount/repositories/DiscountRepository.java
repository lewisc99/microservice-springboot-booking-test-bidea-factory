package com.lewis.discount.repositories;

import com.lewis.discount.domain.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

   Optional<Discount> findByDiscountCode(String discountCode);
}
