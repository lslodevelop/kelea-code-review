package com.inditex.ecommerce.infrastructure.database.repository;

import com.inditex.ecommerce.infrastructure.database.model.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PriceRepositoryH2 extends JpaRepository<PriceEntity, UUID> {

}
