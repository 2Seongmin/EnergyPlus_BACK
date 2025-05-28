package com.kh.ecolog.configuration.filter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kh.ecolog.auth.util.JWTUtil;
import com.kh.ecolog.exception.UserNotFoundException;
import com.kh.ecolog.member.model.dao.MemberMapper;
import com.kh.ecolog.member.model.dto.MemberDTO;
import com.kh.ecolog.member.model.service.MemberService;
import com.kh.ecolog.token.model.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    
    private final JWTUtil jwtUtil;
    private final UserDetailsService usrDetailsService;
    private final MemberMapper memberMapper;
    private final TokenService tokenService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {
        
        final String requestPath = request.getServletPath();
        
        // ✅ 인증 없이 접근 가능한 경로들
        if (
            requestPath.startsWith("/auth/") ||
            requestPath.equals("/admin/cardnews/main") ||
            requestPath.equals("/admin/cardnews/list") ||
            requestPath.matches("^/admin/cardnews/\\d+$") // 예: 상세보기
        ) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // 토큰 형식인 경우
        String token = authHeader.substring(7); // "Bearer " 제거
        log.info("JWT 토큰 : {}", token);
        
        try {
            Claims claims = jwtUtil.parseJwt(token);

            Long userId = jwtUtil.getUserIdFromToken(token);
            String userEmail = jwtUtil.getUserEmailFromToken(token);
            
            // 회원 상태 확인
            MemberDTO member = memberMapper.getMemberByUserId(userId);
            if (member == null) {
                log.warn("존재하지 않는 회원의 접근 시도: {}", userEmail);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("존재하지 않는 회원입니다. 접근이 거부되었습니다.");
                tokenService.deleteUserToken(userId); // 재로그인 방지
                return;
            }
            
            // 탈퇴한 회원 체크
            if ("N".equals(member.getStatus())) {
                log.warn("탈퇴한 회원의 접근 시도: {}", userEmail);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("탈퇴한 회원입니다.");
                tokenService.deleteUserToken(userId);
                return;
            }
            
            String role = jwtUtil.getRoleFromToken(token);
            
            log.info(role);
            
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
            	
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority(role));
                
                UserDetails userDetails = usrDetailsService.loadUserByUsername(userEmail);
                
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                    
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                log.debug("사용자 인증 성공: {}, 사용자 ID: {}, 권한 : {}", userEmail, userId, role);
            }
            
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("토큰이 만료되었습니다. 다시 로그인해주세요.");
            return;            
        } catch(Exception e) {
            log.error("JWT 처리 중 오류 발생: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("유효하지 않은 토큰입니다.");
            return;      	
        }
        
        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }
}