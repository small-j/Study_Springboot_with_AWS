package com.smallj.study.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

//mybatis에서는 Dao라고 불렸다. DB Layer 접근자
public interface PostsRepository extends JpaRepository<Posts, Long> {
    //JpaRepository<>를 상속하게 되면 자동으로 기본적인 CRUD메소드가 생성됨
}
