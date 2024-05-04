package tn.spring.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tn.spring.entity.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    // Define a method to find all posts with pagination
    List<Post> findAll();

}