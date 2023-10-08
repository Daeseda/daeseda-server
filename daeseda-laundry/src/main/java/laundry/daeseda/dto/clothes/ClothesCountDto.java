package laundry.daeseda.dto.clothes;

import laundry.daeseda.dto.order.OrderDto;
import laundry.daeseda.entity.clothes.ClothesEntity;
import laundry.daeseda.entity.order.OrderEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Builder
public class ClothesCountDto {

    private ClothesDTO clothes;
    private int count;

}
