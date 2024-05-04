package tn.spring.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.spring.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	User findByEmail(String email);
}
