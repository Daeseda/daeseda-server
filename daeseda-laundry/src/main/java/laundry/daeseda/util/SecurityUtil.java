package laundry.daeseda.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtil {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() {}

    public static Optional<String> getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            logger.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(username);
    }
}

// 현재 사용자 식별: 현재 로그인한 사용자의 식별 정보를 얻음 - 사용자의 아이디, 이메일 또는 다른 식별자
// 사용자 로그: 애플리케이션 로그에 현재 사용자의 정보를 기록 - 로그에 누가 어떤 작업을 수행했는지 추적하기 위해 유용
// 사용자 관련 작업: 현재 사용자의 정보를 기반으로 특정 작업을 수행할 때 사용 - 현재 사용자의 권한을 확인하고 특정 엔드포인트에 접근을 제어할 때 사용