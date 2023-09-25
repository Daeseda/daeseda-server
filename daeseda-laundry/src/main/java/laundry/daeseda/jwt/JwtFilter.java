package laundry.daeseda.jwt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            String key = "JWT_TOKEN:" + tokenProvider.getUserPk(jwt);
            String storedToken = (String) redisTemplate.opsForValue().get(key);
            if(redisTemplate.hasKey(key) && storedToken != null) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
            }
        } else {
            log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
    // 필터 체인을 통해 요청을 처리하고, 필터가 필요한 작업을 수행한 후 요청을 다음 필터로 전달

    private String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}

// JWT 토큰 검증: 클라이언트로부터 받은 JWT 토큰의 유효성을 검사, 토큰이 유효한지 여부는 토큰 서명의 검증과 만료 시간의 확인 등을 통해 판단
// 인증 정보 설정: 유효한 JWT 토큰이 있다면, 해당 토큰을 기반으로 사용자를 인증하고, Spring Security의 SecurityContextHolder에 인증 정보를 설정 -> 사용자는 인증된 상태로 애플리케이션에 접근
// 로깅: 필터는 검증 및 처리 과정 중에 발생하는 이벤트를 로깅하여 디버깅 및 감시를 용이하게 함