package tn.spring.impl;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tn.spring.dto.PostDTO;
import tn.spring.entity.Post;
import tn.spring.interfac.PostInterface;
import tn.spring.repo.PostRepository;

@Service
public class PostServiceImpl implements PostInterface {

    @Autowired
    private PostRepository postRepo;

    @Override
    public void addPost(Post post) {
        postRepo.save(post);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepo.findAll();
        return posts.stream()
                .map(post -> new PostDTO(
                        post.getPost_id(), 
                        post.getContent(), 
                        post.getCreatedAt(), 
                        post.getUser() != null ? post.getUser().getUser_id() : null))
                .collect(Collectors.toList());
    }
}