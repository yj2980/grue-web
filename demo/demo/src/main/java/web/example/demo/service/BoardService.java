package web.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.example.demo.dto.BoardDto;
import web.example.demo.model.Board;
import web.example.demo.model.User;
import web.example.demo.repository.BoardRepository;
import web.example.demo.repository.UserRepository;

@Service
public class BoardService {
	private static final int PAGE_POST_COUNT = 4;
	private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
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

	public List<BoardDto> findBoardList(Integer pageNumber) {
		Page<Board> boards = boardRepository.findAll(PageRequest.of(pageNumber - 1, PAGE_POST_COUNT));

		return boards.stream().map(BoardDto::toDTO).collect(Collectors.toList());
	}

	// 페이징
	@Transactional
	public Long getBoardCount() {
		return boardRepository.count();
	}

	public Integer[] getPageList(Integer curPageNum) {
		Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

		// 총 게시글 갯수
		Double postsTotalCount = Double.valueOf(this.getBoardCount());

		// 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
		Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

		// 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
		Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
				? curPageNum + BLOCK_PAGE_NUM_COUNT
				: totalLastPageNum;

		// 페이지 시작 번호 조정
		curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

		// 페이지 번호 할당
		for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
			pageList[idx] = val;
		}

		return pageList;
	}
}
