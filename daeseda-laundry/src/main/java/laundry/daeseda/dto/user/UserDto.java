package laundry.daeseda.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {
    private Long userId;

    @NotNull
    @Size(min = 3, max = 50)
    private String userEmail;

    private String userName;
    private String userNickname;
    private String userPhone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String userPassword;
}
