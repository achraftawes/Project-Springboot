package tn.spring.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.spring.dto.PostDTO;
import tn.spring.entity.Post;
import tn.spring.entity.User;
import tn.spring.impl.PostServiceImpl;
import tn.spring.impl.UserServiceImpl;
import tn.spring.interfac.PostInterface;
import tn.spring.security.JwtTokenUtil;


@RestController
public class PostController {

    @Autowired
    private PostInterface postImpl;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private PostServiceImpl postService;

    @PostMapping("/add-post")
    @ResponseBody
    public ResponseEntity<String> addPost(@RequestHeader("Authorization") String token,
                                          @RequestBody Map<String, String> postDetails) {
        String userEmail = jwtTokenUtil.extractEmail(token.substring(7));
        User user = userService.retrieveUserByEmail(userEmail);
        if (user != null) {
            Post post = new Post();
            post.setContent(postDetails.get("content"));
            post.setCreatedAt(new Date());
            post.setUser(user);
            postImpl.addPost(post);
            return ResponseEntity.ok("Post added successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
        }
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getPosts() {
        List<PostDTO> postDTOs = postService.getAllPosts();
        return ResponseEntity.ok(postDTOs);
    }
    
    
}