package com.cicd.todoapi.security.filter;

import com.cicd.todoapi.domain.Role;
import com.cicd.todoapi.dto.MemberUserDetail;
import com.cicd.todoapi.util.JWTUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class JWTCheckFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Preflight 필터 체크 X (Ajax CORS 요청 전에 날리는것)
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String requestURI = request.getRequestURI();
        log.info("************** JWTCheckFilter - shouldNotFilter : requestURI : {}", requestURI);
        // /api/member/.. 경로 요청은 필터 체크 X
        if (requestURI.startsWith("/api/member/")) {
            return true;
        }
        // 이미지 경로 요청은 필터 체크 X
        /*if(requestURI.startsWith("/api/products/view/")) {
            return true;
        }*/
        return false;
    }
    // 필터 생략할것 지정하는 메서드 추가 (OncePer... 에 있는 메서드 오버라이딩)
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("************ JWTCheckFilter - doFilterInternal!");

        String authValue = request.getHeader("Authorization");
        log.info("********* doFilterInternal - authValue : {}", authValue);
        // Bearer XxxxxxxxaccessToken값
        try {
            String accessToken = authValue.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);
            log.info("********* doFilterInternal - claims : {}", claims);

            // 인증 정보 claims로 MemberDTO 구성 -> 시큐리티에 반영 추가 (시큐리티용 권한)
            Long id = (Long) claims.get("id");
            String email = (String) claims.get("email");
            String password = (String) claims.get("password");
            Role role = Role.valueOf((String) claims.get("role"));

            MemberUserDetail userDetail = new MemberUserDetail(id, email, password, role);
            log.info("******** doFileterInternal - userDetail : {}", userDetail);

            // 시큐리티 인증 추가 : JWT와 SpringSecurity 로그인상태 호환되도록 처리
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userDetail, password, userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response); // 다음필터 이동해라~
        }catch (Exception e) {
            // Access Token 검증 예외 처리 (검증하다 실패하면 우리가만든 예외 발생-> 그에 따른 처리하기)
            log.error("*********** JWTCheckFilter error!!!");
            log.error(e.getMessage());

            // 에러라고 응답해줄 메세지 생성 -> 전송
            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.println(msg);
            writer.close();
        }
    }
}
