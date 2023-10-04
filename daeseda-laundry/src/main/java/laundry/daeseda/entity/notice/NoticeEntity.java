package laundry.daeseda.entity.notice;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@EnableJpaAuditing
@Table(name = "notice")
public class NoticeEntity {
    @Id
    @Column(name = "notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column
    private String noticeCategory;

    @Column
    private String noticeTitle;

    @Column
    private String noticeContent;

    @Column
    @CreatedDate
    private LocalDateTime createDate;

    @Column
    @LastModifiedDate
    private LocalDateTime updateDate;
}
