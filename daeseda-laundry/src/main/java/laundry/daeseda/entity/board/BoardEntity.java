package laundry.daeseda.entity.board;

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
@Table(name = "board")
public class BoardEntity {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column
    private String boardCategory;

    @Column
    private String boardTitle;

    @Column
    private String boardContent;

    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "modDate")
    private LocalDateTime modDate;
}
