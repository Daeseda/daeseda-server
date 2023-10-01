package laundry.daeseda.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddressListDto {
    private List<AddressDto> addressList = new ArrayList<>();

    // 다른 클래스에서 addressList 필드에 접근할 때 사용할 메서드
    public void addAddress(AddressDto addressDto) {
        this.addressList.add(addressDto);
    }

    public List<AddressDto> getAddressList() {
        return this.addressList;
    }

    public void setAddressList(List<AddressDto> addressList) {
        this.addressList = addressList;
    }
}
