package com.smallj.study.springboot.service.posts;

import com.smallj.study.springboot.domain.posts.Posts;
import com.smallj.study.springboot.domain.posts.PostsRepository;
import com.smallj.study.springboot.web.dto.PostsResponseDto;
import com.smallj.study.springboot.web.dto.PostsSaveRequestDto;
import com.smallj.study.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//final이 선언된 모든 필드를 인자값으로 하는 생성자 생성해줌.
// 롬복 어노테이션을 사용하면 생성자의 변경으로 인한 코드 변경을 줄일 수 있음
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        //영속성 컨텍스트 때문에 쿼리를 날리는 부분이 없다.
        //영속성 컨텍스트란?
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }
}
