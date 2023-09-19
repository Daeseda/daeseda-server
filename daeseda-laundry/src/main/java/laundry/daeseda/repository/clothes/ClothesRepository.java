package laundry.daeseda.repository.clothes;

import laundry.daeseda.entity.clothes.ClothesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothesRepository extends JpaRepository<ClothesEntity, Long> {
}
