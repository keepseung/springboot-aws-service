package com.keepseung.springbootawsservice.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LoginUser 어노테이션 생성
 * 세션을 받는 중복된 코드를 메소드의 파리미터로 받을 수 있게 하기 위함
 */

@Target(ElementType.PARAMETER) // 메소드의 파리미터로 선언된 객체에서만 LoginUser 어노테이션을 사용 가능
@Retention(RetentionPolicy.RUNTIME)
// LoginUser을 어노테이션 클래스로 지정함
public @interface LoginUser {
}