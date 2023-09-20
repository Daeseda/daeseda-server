package laundry.daeseda.service.user;

import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository.findOneWithAuthoritiesByUserEmail(userEmail)
                .map(user -> createUser(userEmail, user))
                .orElseThrow(() -> new UsernameNotFoundException(userEmail + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String userEmail, UserEntity userEntity) {
        if (!userEntity.isActivated()) {
            throw new RuntimeException(userEmail + " -> 활성화되어 있지 않습니다.");
        }
        List<GrantedAuthority> grantedAuthorities = userEntity.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(userEntity.getUserName(),
                userEntity.getUserPassword(),
                grantedAuthorities);
    }
}
