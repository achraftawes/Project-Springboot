package tn.spring.interfac;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tn.spring.dto.PostDTO;
import tn.spring.entity.Post;

public interface PostInterface {

    void addPost(Post post);

    List<PostDTO> getAllPosts(); 
}
