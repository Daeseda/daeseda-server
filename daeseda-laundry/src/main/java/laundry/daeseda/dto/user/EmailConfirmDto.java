package laundry.daeseda.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfirmDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String userEmail;

    @NotNull
    @Size(min = 12, max = 12)
    private String code;
}
