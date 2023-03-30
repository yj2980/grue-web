package web.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.example.demo.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
	List<Board> findAll();

	Page<Board> findByCategory(PageRequest of, String category);
	Long countByCategory(String category);
}

