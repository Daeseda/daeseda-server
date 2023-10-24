package laundry.daeseda.service.user;

import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.entity.user.AddressEntity;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.repository.user.AddressRepository;
import laundry.daeseda.repository.user.UserRepository;
import laundry.daeseda.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public int createAddress(AddressDto addressDto) {
        UserEntity userEntity = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        AddressEntity addressEntity = AddressEntity.builder()
                .addressName(addressDto.getAddressName())
                .addressRoad(addressDto.getAddressRoad())
                .addressZipcode(addressDto.getAddressZipcode())
                .addressDetail(addressDto.getAddressDetail())
                .user(userEntity)
                .build();
        addressRepository.save(addressEntity);
        return 1;
    }

    @Override
    public List<AddressDto> getMyAddressList() {
        // 현재 사용자 아이디 가져오기
        String currentUserEmail = SecurityUtil.getCurrentUsername().get();

        // 사용자 이메일을 기반으로 사용자 엔티티 조회
        UserEntity userEntity = userRepository.findByUserEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 사용자 엔티티를 기반으로 해당 사용자의 주소 목록 조회
        List<AddressEntity> addressEntities = addressRepository.findByUser(userEntity);

        // 주소 목록을 AddressDto로 매핑하고 AddressListDto에 추가
        List<AddressDto> addressDtos = new ArrayList<>();
        boolean isDefault = false;

        for (AddressEntity addressEntity : addressEntities) {
            if(addressEntity.getAddressId() == userEntity.getDefaultAddress().getAddressId())
                isDefault = true;
            System.out.println(addressEntity.getAddressId());
            System.out.println(userEntity.getDefaultAddress().getAddressId());

            AddressDto addressDto = AddressDto.builder()
                    .addressId(addressEntity.getAddressId())
                    .addressName(addressEntity.getAddressName())
                    .addressRoad(addressEntity.getAddressRoad())
                    .addressZipcode(addressEntity.getAddressZipcode())
                    .addressDetail(addressEntity.getAddressDetail())
                    .defaultAddress(isDefault)
                    .build();
            addressDtos.add(addressDto);

            isDefault = false;
        }

        return addressDtos;
    }

    @Override
    public int delete(AddressDto addressDto) {
        addressRepository.deleteById(addressDto.getAddressId());
        return 1;
    }


}
