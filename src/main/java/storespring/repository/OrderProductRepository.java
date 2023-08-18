package storespring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storespring.entity.OrderProductEntity;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {
}