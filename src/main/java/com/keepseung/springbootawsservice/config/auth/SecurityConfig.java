package com.keepseung.springbootawsservice.config.auth;

import com.keepseung.springbootawsservice.web.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // 스프링 시큐리티 설정들을 활성화 함
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // h2-console 화면을 사용하기 위해 해당 옵션들을 disable 한다.
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests() // Url 별 권한 관리를 설정하는 옵션의 시작점
                    // 권한 관리 대상을 지정함
                    // "/" 포함한 지정된 URL들은 전체 열람 권한을 줌
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                    // "/api/v1/**"은 Role.USER 권한을 가진 사람만 가능
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    // 나머지 URL 요청은 모두 인증된 사용자만 가능
                    .anyRequest().authenticated()
                .and()
                    // 로그아웃 성공시 "/" 주소로 이동함
                    .logout()
                     .logoutSuccessUrl("/")
                .and()
                    // oauth2Login 로그인 설정에 대한 진입점
                    .oauth2Login()
                        // oauth2Login 로그인 성공 이후사용자 정보를 가져올 때 설정을 담당함
                        .userInfoEndpoint()
                            // 소셜 로그인 성공시 후속 조치를 진행할 UserService
                            .userService(customOAuth2UserService);
    }
}