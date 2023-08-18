package storespring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import storespring.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}