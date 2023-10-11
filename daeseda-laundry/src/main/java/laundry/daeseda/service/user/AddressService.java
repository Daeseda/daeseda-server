package laundry.daeseda.service.user;

import laundry.daeseda.dto.address.AddressDto;

import java.util.List;

public interface AddressService {

    int createAddress(AddressDto addressDto);

    List<AddressDto> getMyAddressList();

    int delete(AddressDto addressDto);
}
