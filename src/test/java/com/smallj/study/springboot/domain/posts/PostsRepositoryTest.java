package com.smallj.study.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest     //별다른 설정이 없으면 h2 자동 실행
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    //단위 테스트가 끝날때마다 수행되는 메소드.
    // 여러 테스트 동시 진행 시 h2에 남아있는 데이터때문에 발생하는 문제를 방지하기 위해 삭제 작업을 한다.
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본몬";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("smallj@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0,0,0);
        postsRepository.save(Posts.builder()
                .title("hello")
                .content("world")
                .author("Mr.hello")
                .build()
        );

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>> createDate=" + posts.getCreatedDate() + "\n");
        System.out.println(">>>>>> modifiedDate=" + posts.getModifiedDate() + "\n");
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
