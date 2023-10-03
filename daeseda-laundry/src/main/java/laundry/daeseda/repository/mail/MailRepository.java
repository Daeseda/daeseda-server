package laundry.daeseda.repository.mail;

import laundry.daeseda.entity.mail.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<MailEntity, Long> {
}
