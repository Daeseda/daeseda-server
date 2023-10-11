package laundry.daeseda.dto.order;

import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.dto.clothes.ClothesDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderFormDto {

    private List<AddressDto> address;
    private List<ClothesDTO> clothes;

}
