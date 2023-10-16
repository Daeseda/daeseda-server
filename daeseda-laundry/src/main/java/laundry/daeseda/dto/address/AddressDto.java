package laundry.daeseda.dto.address;

import laundry.daeseda.dto.user.AuthorityDto;
import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.user.AddressEntity;
import laundry.daeseda.entity.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Column;
import java.util.stream.Collectors;


@Getter
@Builder
public class AddressDto {

    private Long addressId;
    private String addressName;
    private String addressRoad;
    private String addressDetail;
    private String addressZipcode;

    public static AddressDto from(AddressEntity address) {
        if(address == null)
            return null;

        return AddressDto.builder()
                .addressId(address.getAddressId())
                .addressName(address.getAddressName())
                .addressRoad(address.getAddressRoad())
                .addressDetail(address.getAddressDetail())
                .addressZipcode(address.getAddressZipcode())
                .build();
    }

}
