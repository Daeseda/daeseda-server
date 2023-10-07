package laundry.daeseda.dto.address;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddressListDto {
    private List<AddressDto> addressList = new ArrayList<>();

    public void setAddressList(List<AddressDto> addressList) {
        this.addressList = addressList;
    }
}
