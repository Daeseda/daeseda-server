package laundry.daeseda.repository.user;

import laundry.daeseda.constant.OrderStatus;
import laundry.daeseda.entity.user.AddressEntity;
import laundry.daeseda.entity.user.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<UserEntity> findOneWithAuthoritiesByUserEmail(String userEmail);

    @EntityGraph(attributePaths = "authorities")
    Optional<UserEntity> findOneWithAuthoritiesByUserName(String userName);

    Optional<UserEntity> findByUserEmail(String userEmail);

    @Modifying
    @Query("update UserEntity u SET u.defaultAddress = :address WHERE u.userId = :userId")
    void updateDefaultAddress(AddressEntity address, Long userId);
}
