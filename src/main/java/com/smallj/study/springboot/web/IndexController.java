package com.smallj.study.springboot.web;

import com.smallj.study.springboot.service.posts.PostsService;
import com.smallj.study.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());    //view에 전달할 데이터를 key, value 쌍으로 전달

        return "index";     //이 문자열 앞에 파일 경로가 뒤에 확장자가 붙어서 index.mustache가 된다.
                            //src/main/resources/templates/index.mustache
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);       //url에 붙어 전달된 id를 통해 db에 저장된 해당 게시글의 정보를 불러옴
        model.addAttribute("post",dto);         //그 페이지에 key값 post로 전달

        return "posts-update";
    }
}
