package com.keepseung.springbootawsservice.web.domain.auth;

import com.keepseung.springbootawsservice.web.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화시켜 줍니다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // h2-console 화면을 사용하기 위해 해당 옵션들을 disable 합니다.
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()// URL별 권한 관리를 설정하는 옵션의 시작점입니다.
                // 권한 관리 대상을 지정하는 옵션입니다. "/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 주었습니다.
                .antMatchers("/", "/css/**", "/images/**",
                        "/js/**", "/h2-console/**").permitAll()
                // "/api/v1/**"주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 했습니다.
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest() // 설정된 값들 이외 나머지 URL들을 나타냅니다
                .authenticated() // authenticated()을 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용하게 됩니다.
                .and()
                .logout()// 로그아웃 기능에 대한 여러 설정의 진입점입니다
                .logoutSuccessUrl("/")// 로그아웃 성공 시 / 주소로 이동합니다.
                .and()
                .oauth2Login()// OAuth 2 로그인 기능에 대한 여러 설정의 진입점입니다
                .userInfoEndpoint()// OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당합니다.
                .userService(customOAuth2UserService); // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록합니다.

    }
}
