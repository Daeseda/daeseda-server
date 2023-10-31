package laundry.daeseda.repository.review;

import laundry.daeseda.entity.review.ReviewEntity;
import laundry.daeseda.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Modifying
    @Query("delete from AddressEntity a WHERE a.user = :user")
    void deleteByUserId(UserEntity user);

}
