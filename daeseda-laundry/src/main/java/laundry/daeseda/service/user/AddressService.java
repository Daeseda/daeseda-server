package laundry.daeseda.service.user;

import laundry.daeseda.dto.user.AddressDto;
import laundry.daeseda.dto.user.AddressListDto;
import laundry.daeseda.entity.user.AddressEntity;

import java.util.ArrayList;
import java.util.List;

public interface AddressService {

    int createAddress(AddressDto addressDto);

    List<AddressListDto> getMyAddressList();


}
