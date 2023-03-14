package web.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.example.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByEmail(String email);

	User findByEmail(String email);
	User findByPhoneNumber(String phoneNumber);

}
