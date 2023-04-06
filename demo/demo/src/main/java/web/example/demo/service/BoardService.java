package web.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import web.example.demo.dto.BoardDto;
import web.example.demo.dto.CityDto;
import web.example.demo.model.Board;
import web.example.demo.model.City;
import web.example.demo.model.User;
import web.example.demo.repository.BoardRepository;
import web.example.demo.repository.CityRepository;
import web.example.demo.repository.UserRepository;
import web.example.demo.util.BoardCategoryEnum;

@Service
public class BoardService {
	private static final int PAGE_POST_COUNT = 25;
	private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CityRepository cityRepository;

	// optional to entity
	public Board findById(int id) {
		return boardRepository.findById(id).get();
	}

	public void saveBoardInfo(BoardDto.Create boardDto) {
		boardRepository.save(toEntity(boardDto));
	}

	public void saveCityInfo(CityDto.Create cityDto, MultipartFile file) throws IOException {
		cityRepository.save(toEntity(cityDto, file));
	}

	public void deleteBoardPost(int id) {
		boardRepository.deleteById(id);
	}

	public BoardDto.Post getPost(Integer id) {
		Board board = findById(id);

		return BoardDto.Post.toDTO(board);
	}


	// 회원 가입된 유저가 맞는지 확인
	private boolean verifyUserMembership(String username) {
		System.out.println("username : " + username);

		if (username == "") {
			return false;
		}

		return true;
	}

	private City toEntity(CityDto.Create cityDto, MultipartFile file) throws IOException {

		return cityDto.toBoard(file);
	}

	private Board toEntity(BoardDto.Create boardDto) {
		User user = userRepository.findByUsername(boardDto.getAuthor());

		return boardDto.toBoard(user);
	}

	public List<BoardDto.ShowBoardDTO> findBoardList(Integer pageNumber, String category) {
		System.out.println("카테고리 : " + category);
		Page<Board> boards;
		category = BoardCategoryEnum.findSelectByCategoryValue(category);

		if (!category.equals(BoardCategoryEnum.GENERAL.getSelectValue())) {
			boards = boardRepository.findByCategory(PageRequest.of(pageNumber - 1, PAGE_POST_COUNT), category);
		} else {
			boards = boardRepository.findAll(PageRequest.of(pageNumber - 1, PAGE_POST_COUNT));
		}

		System.out.println("\n\n 결과값 : " + category + " || " + boards);

		return boards.stream().filter(v -> v.getUserID() != null).map(BoardDto.ShowBoardDTO::toDTO).collect(Collectors.toList());
	}

	public List<CityDto.ShowCityDTO> findCityList() {
		List<City> city;

		city = cityRepository.findAll();

		return city.stream().map(CityDto.ShowCityDTO::toDTO).collect(Collectors.toList());
	}

	public List<BoardDto.ShowBoardDTO> searchPostByKeyword(Integer pageNumber, String keyword) {
		Page<Board> boards = boardRepository.findByContent(PageRequest.of(pageNumber - 1, PAGE_POST_COUNT), keyword);
		
		return boards.stream().filter(v -> v.getUserID() != null).map(BoardDto.ShowBoardDTO::toDTO).collect(Collectors.toList());
	}


	public List<Integer> getPageList(Integer currentPageNumber, String seq, String type) {
		List<Integer> pageList;

		Integer totalLastPageNumber = countLastPageNumber(seq, type);
		currentPageNumber = setPageStartIndex(currentPageNumber);
		Integer blockLastPageNumber = countBlockLastPageNumber(totalLastPageNumber, currentPageNumber);

		pageList = setCurrentPage(currentPageNumber, blockLastPageNumber);

		return pageList;
	}

	// 페이지 번호 할당
	private List<Integer> setCurrentPage(Integer currentPageNumber, Integer blockLastPageNumber) {
		List<Integer> pageList = new ArrayList<>();

		for (int value = currentPageNumber, i = 0; value <= blockLastPageNumber; value++, i++) {
			try {
				pageList.set(i, value);
			} catch (Exception e) {
				pageList.add(value);
			}
		}

		return pageList;
	}

	// 페이지 시작 번호 조정
	private Integer setPageStartIndex(Integer currentPageNumber) {
		if (currentPageNumber <= 3) {
			currentPageNumber = 1;
		}
		if (currentPageNumber > 3) {
			currentPageNumber -= 2;
		}

		return currentPageNumber;
	}

	// 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
	private Integer countLastPageNumber(String seq, String type) {
		Double postsTotalCount = null;
		
		if (type.equals("CONTENT")) {
			postsTotalCount = Double.valueOf(boardRepository.countByContent(seq));
		}
		if (type.equals("CATEGORY")) {
			postsTotalCount = Double.valueOf(this.countPostByCategory(seq));
		}
		
		Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

		return totalLastPageNum;
	}

	@Transactional
	private Long countPostByCategory(String category) {
		if (category.equals(BoardCategoryEnum.GENERAL.getCategoryValue())) {
			return boardRepository.count();
		}
		
		return boardRepository.countByCategory(BoardCategoryEnum.findSelectByCategoryValue(category));
	}

	// 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
	private Integer countBlockLastPageNumber(Integer totalPageNumber, Integer currentPageNumber) {
		Integer blockLastPageNumber = totalPageNumber;

		if (totalPageNumber > currentPageNumber +  BLOCK_PAGE_NUM_COUNT) {
			blockLastPageNumber = currentPageNumber + BLOCK_PAGE_NUM_COUNT;
		}

		return blockLastPageNumber;
	}

}


