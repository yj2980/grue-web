package web.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.example.demo.dto.BoardDto;
import web.example.demo.model.Board;
import web.example.demo.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@PostMapping("writingBoard")
	public String writeBoard(BoardDto board) {
		System.out.println("board List \n ------------------------------\n"+ board.toString());

		boardService.saveBoardInfo(board);

		// CHORE : 이거 show로 바꿔야한다.~!~~!~!~!
		return "redirect:/community/list";
	}

	@GetMapping({"community","community/list"})
	public String community(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNumber) {
		List<BoardDto> board = boardService.findBoardList(pageNumber);
		Integer[] pageList = boardService.getPageList(pageNumber);

		for (int i = 0; i < pageList.length; i++) {
			System.out.println("page List : " + pageList[i]);
		}

		model.addAttribute("writingList", board);
		model.addAttribute("pageList", pageList);

		return "community/list";
	}

}
