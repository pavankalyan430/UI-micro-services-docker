package com.smartbazaar.priceservice.repository;

import com.smartbazaar.priceservice.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
