package laundry.daeseda.service.user;

import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.dto.address.AddressListDto;

import java.util.List;

public interface AddressService {

    int createAddress(AddressDto addressDto);

    List<AddressListDto> getMyAddressList();

    int delete(AddressDto addressDto);
}
