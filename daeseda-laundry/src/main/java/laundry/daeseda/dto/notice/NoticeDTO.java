package laundry.daeseda.dto.notice;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel(value = "공지사항 정보")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NoticeDTO {
    @ApiModelProperty(value = "공지사항 ID", example = "자동 할당")
    private Long noticeId;

    @ApiModelProperty(value = "공지사항 카테고리", example = "공지사항")
    private String noticeCategory;

    @ApiModelProperty(value = "공지사항 제목", example = "제목")
    private String noticeTitle;

    @ApiModelProperty(value = "공지사항 내용", example = "내용")
    private String noticeContent;

    @ApiModelProperty(value = "생성 시간", example = "자동 할당")
    private LocalDateTime regDate;

    @ApiModelProperty(value = "수정 시간", example = "자동 할당")
    private LocalDateTime modDate;
}
