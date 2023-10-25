package laundry.daeseda.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {

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

    private Set<AuthorityDto> authorityDtoSet;
    private AddressDto addressDto;

    public static UserDto from(UserEntity user) {
        if(user == null)
            return null;

        if(user.getDefaultAddress() != null) {
            return UserDto.builder()
                    .userName(user.getUserName())
                    .userNickname(user.getUserNickname())
                    .userPhone(user.getUserPhone())
                    .userEmail(user.getUserEmail())
                    .authorityDtoSet(user.getAuthorities().stream()
                            .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                            .collect(Collectors.toSet()))
                    .addressDto(AddressDto.builder()
                            .addressId(user.getDefaultAddress().getAddressId())
                            .addressRoad(user.getDefaultAddress().getAddressRoad())
                            .addressName(user.getDefaultAddress().getAddressName())
                            .addressDetail(user.getDefaultAddress().getAddressDetail())
                            .addressZipcode(user.getDefaultAddress().getAddressZipcode())
                            .build())
                    .build();
        }

        return UserDto.builder()
                .userName(user.getUserName())
                .userNickname(user.getUserNickname())
                .userPhone(user.getUserPhone())
                .userEmail(user.getUserEmail())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
