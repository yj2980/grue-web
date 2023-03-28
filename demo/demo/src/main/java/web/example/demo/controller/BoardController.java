package web.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import web.example.demo.dto.BoardDto;
import web.example.demo.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@PostMapping("writingBoard")
	public String writeBoard(BoardDto board) {
		System.out.println(board.toString());

		boardService.saveBoardInfo(board);

		// CHORE : 이거 show로 바꿔야한다.~!~~!~!~!
		return "Community";
	}
}
