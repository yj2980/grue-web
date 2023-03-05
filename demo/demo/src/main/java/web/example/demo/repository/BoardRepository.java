package web.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.example.demo.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
