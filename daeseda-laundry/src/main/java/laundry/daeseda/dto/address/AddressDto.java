package laundry.daeseda.dto.address;

import laundry.daeseda.entity.user.AddressEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressDto {

    private Long addressId;
    private String addressName;
    private String addressDetail;
    private String addressZipcode;

    public static AddressDto from(AddressEntity address) {
        if(address == null)
            return null;

        return AddressDto.builder()
                .addressId(address.getAddressId())
                .addressName(address.getAddressName())
                .addressDetail(address.getAddressDetail())
                .addressZipcode(address.getAddressZipcode())
                .build();
    }

}
