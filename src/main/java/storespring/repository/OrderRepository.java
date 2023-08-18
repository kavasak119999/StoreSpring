package storespring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storespring.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}