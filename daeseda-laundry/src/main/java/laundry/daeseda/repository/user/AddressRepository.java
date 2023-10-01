package laundry.daeseda.repository.user;

import laundry.daeseda.entity.user.AddressEntity;
import laundry.daeseda.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
    List<AddressEntity> findByUserId(UserEntity userEntity);
}
