package laundry.daeseda.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private String memberName;
    private String memberPhone;
    private String memberNickname;
    private String memberEmail;
    private String memberPassword;
}
