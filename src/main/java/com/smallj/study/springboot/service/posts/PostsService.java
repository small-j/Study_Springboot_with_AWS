package com.smallj.study.springboot.service.posts;

import com.smallj.study.springboot.domain.posts.Posts;
import com.smallj.study.springboot.domain.posts.PostsRepository;
import com.smallj.study.springboot.web.dto.PostsListResponseDto;
import com.smallj.study.springboot.web.dto.PostsResponseDto;
import com.smallj.study.springboot.web.dto.PostsSaveRequestDto;
import com.smallj.study.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional      //복수의 쿼리를 원자적으로 사용하기 위해서
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        //영속성 컨텍스트 때문에 쿼리를 날리는 부분이 없다.
        //영속성 컨텍스트란?
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    //Transactional은 현재 로직 즉 메서드를 실행할 때 error가 날 경우 데이터베이스를 rollback해주려고 선언한다.
    // 따라서 조회 로직은 데이터베이스를 변경하는 부분이 없기에 rollback해줄 필요가 없어 쓰지 않는다.
    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)     //read 이외의 동작을 할 경우 Exception 발생
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)     //.map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)      //존재하는 게시글인지 확인
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);      //Repository에서 삭제 연산 수행하도록함. JpaRepostitory 지원하는 메소드
    }
}
