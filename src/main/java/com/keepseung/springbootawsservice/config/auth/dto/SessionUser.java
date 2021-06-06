package com.keepseung.springbootawsservice.config.auth.dto;

import com.keepseung.springbootawsservice.web.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 세션에 사용자 정보를 저장하기 위한 dto 클래스
 * User 엔터티를 그대로 사용하지 않기 위함
 * 세션에 저장하기 직렬화를 해야 함
 * 엔터티에 직렬화를 구현하면 @OneToMany, @ManyToOne 등의 요소 들에서 성능 이슈가 발생함
 * */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}