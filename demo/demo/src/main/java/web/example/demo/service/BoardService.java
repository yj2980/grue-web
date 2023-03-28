package web.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.example.demo.dto.BoardDto;
import web.example.demo.model.Board;
import web.example.demo.model.User;
import web.example.demo.repository.BoardRepository;
import web.example.demo.repository.UserRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private UserRepository userRepository;

	public void saveBoardInfo(BoardDto boardDto) {
		boardRepository.save(toEntity(boardDto));
	}

	private Board toEntity(BoardDto boardDto) {
		User user = userRepository.findByUsername(boardDto.getAuthor());

		return boardDto.toBoard(user);
	}
}
