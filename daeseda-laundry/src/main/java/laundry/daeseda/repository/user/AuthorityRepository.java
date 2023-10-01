package laundry.daeseda.repository.user;

import laundry.daeseda.entity.user.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {
    AuthorityEntity findByAuthorityName(String authorityName);
}
