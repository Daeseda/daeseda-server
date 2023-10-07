package laundry.daeseda.entity.mail;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "mails")

@Setter
@NoArgsConstructor
public class MailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mailId;

    private String userEmail;
    private String verificationCode;
}
