package laundry.daeseda.entity.reply;

import laundry.daeseda.entity.board.BoardEntity;
import laundry.daeseda.entity.user.UserEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@EnableJpaAuditing
@Table(name = "reply")
public class ReplyEntity {

    @Id
    @Column(name = "reply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column
    private String userNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @Column
    private String replyContent;

    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "modDate")
    private LocalDateTime modDate;
}
