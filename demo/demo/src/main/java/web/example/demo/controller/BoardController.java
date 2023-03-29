package web.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.example.demo.dto.BoardDto;
import web.example.demo.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@PostMapping("writingBoard")
	public String writeBoard(BoardDto.Create board) {
		boardService.saveBoardInfo(board);

		return "redirect:community";
	}

	@GetMapping({"community","community/list"})
	public String community(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNumber) {
		List<BoardDto.Search> board = boardService.findBoardList(pageNumber);
		List<Integer> pageList = boardService.getPageList(pageNumber);

		model.addAttribute("writingList", board);
		model.addAttribute("pageList", pageList);

		return "community";
	}

	// 게시물 상세 페이지
	@GetMapping("community/postDetail/{no}")
	public String postDetail(@PathVariable("no") Integer no, Model model) {
		BoardDto.Post boardDto = boardService.getPost(no);

		model.addAttribute("boardDto", boardDto);

		return "postDetail";
	}

	@GetMapping("community/postUpdate/{no}")
	public String postUpdate(@PathVariable("no") Integer no, Model model) {
		BoardDto.Post boardDto = boardService.getPost(no);

		model.addAttribute("boardDto", boardDto);

		return "postUpdate";
	}


	@PutMapping("community/postUpdate/{no}")
	public String update(BoardDto.Create boardDto) {
		boardService.saveBoardInfo(boardDto);

		return "redirect:/community";
	}

	@DeleteMapping("community/postUpdate/{no}")
	public String delete(@PathVariable("no") Integer no) {
		boardService.deleteBoardPost(no);

		return "redirect:/community";
	}
}
