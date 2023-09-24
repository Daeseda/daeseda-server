package laundry.daeseda.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}

// Spring Security와 JWT 기반 인증을 함께 사용할 때 인증된 사용자의 권한 부족 상황을 처리
// JWT를 사용한 인증에서, 사용자가 유효한 JWT를 가지고 있지만 요청에 필요한 권한을 가지고 있지 않을 때 이 핸들러가 호출
// 사용자가 인증되었지만, 해당 요청에 대한 접근 권한이 없는 경우 HTTP 응답으로 Forbidden 상태 코드 (403)를 반환