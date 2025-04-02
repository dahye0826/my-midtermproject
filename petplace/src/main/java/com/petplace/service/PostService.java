package com.petplace.service;


import com.petplace.dto.PostResponseDto;
import com.petplace.entity.Post;
import com.petplace.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class PostService{
    private final PostRepository postRepository;
    //“Pageable: 어떤 페이지를 불러올지”에 대한 정보를 담는 인터페이스
    //Pageable 객체를 만들 때 사용하는 팩토리 메서드
    //page-1 0을 뺌 (Spring Data JPA의 PageRequest는 0부터 시작)
    public Page<PostResponseDto> getPosts(String search, int page, int size){
        Pageable pageable = PageRequest.of(page-1,size, Sort.by("createdAt").descending());

        Page<Post> postPage;
        if (search != null && !search.trim().isEmpty()) {
            postPage = postRepository.findByTitleContaining(search, pageable);
        } else {
            postPage = postRepository.findAll(pageable);
        }

        return postPage.map(post -> new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getUser().getName(),
                post.getCreatedAt().toLocalDate().toString().split("T")[0],
                post.getUpdatedAt().toLocalDate().toString().split("T")[0],
                post.getViewCount(),
                post.getCommentCount()
        ));



    }



}
