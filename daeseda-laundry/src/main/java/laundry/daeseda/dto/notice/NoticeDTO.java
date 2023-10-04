package laundry.daeseda.dto.notice;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NoticeDTO {
    private Long noticeId;
    private String noticeCategory;
    private String noticeTitle;
    private String noticeContent;
    private LocalDate createDate;
}
