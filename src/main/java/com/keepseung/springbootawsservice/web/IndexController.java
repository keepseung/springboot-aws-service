package com.keepseung.springbootawsservice.web;

import com.keepseung.springbootawsservice.config.auth.LoginUser;
import com.keepseung.springbootawsservice.config.auth.dto.SessionUser;
import com.keepseung.springbootawsservice.web.domain.posts.PostService;
import com.keepseung.springbootawsservice.web.domain.posts.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostService postService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postService.findAllDesc());

        // CustomOAuth2UserService에서 로그인 성공시 세션에 SessionUser을 저장하도록 구성함
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");

        // 세션에 유저 정보가 있는 경우에만 model에 userName값을 등록한다.
        if (user != null){
            model.addAttribute("userName",user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){

        PostsResponseDto dto = postService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}