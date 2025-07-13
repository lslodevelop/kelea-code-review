package com.inditex.ecommerce.infrastructure.database.repository;

import com.inditex.ecommerce.infrastructure.database.model.price.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryH2 extends JpaRepository<PriceEntity, Long> {

    Optional<PriceEntity> findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            Long productId, Long brandId, LocalDateTime startDateBefore, LocalDateTime endDateAfter);
}
