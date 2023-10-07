package laundry.daeseda.dto.order;

import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.dto.address.AddressListDto;
import laundry.daeseda.dto.clothes.ClothesDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderFormDto {

    private List<AddressListDto> address;
    private List<ClothesDTO> clothes;

}
