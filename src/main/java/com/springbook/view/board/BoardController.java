package com.springbook.view.board;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.springbook.biz.board.BoardListVO;
import com.springbook.biz.board.BoardService;
import com.springbook.biz.board.BoardVO;

@Controller
@SessionAttributes("board") // Model에 "board"라는 이름으로 저장되는 데이터가 있다면 그 데이터를 HttpSession에 자동으로 저장
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping("/dataTransform.do")
	@ResponseBody
	public BoardListVO dataTransform(BoardVO vo) {
		vo.setSearchCondition("TITLE");
		vo.setSearchKeyword("");
		List<BoardVO> boardList = boardService.getBoardList(vo);
		BoardListVO boardListVO = new BoardListVO();
		boardListVO.setBoardList(boardList);
		return boardListVO;
	}

	@RequestMapping("/insertBoard.do")
	public String insertBoard(BoardVO vo) throws IOException {
		// 파일 업로드 처리
		MultipartFile uploadFile = vo.getUploadFile();
		if (!uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File("/Users/ino/Desktop" + fileName));
		}

		boardService.insertBoard(vo);
		return "getBoardList.do";
	}

	@RequestMapping("/updateBoard.do")
	public String updateBoard(@ModelAttribute("board") BoardVO vo) {

		boardService.updateBoard(vo);
		return "getBoardList.do";
	}

	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(BoardVO vo) {

		boardService.deleteBoard(vo);
		return "getBoardList.do";
	}

	@RequestMapping("/getBoard.do")
	public String getBoard(BoardVO vo, Model model) {

		model.addAttribute("board", boardService.getBoard(vo)); // Model 정보 저장
		return "getBoard.jsp"; // View 이름 리턴
	}

	// 검색 조건 목록 설정
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		return conditionMap;
	}

	@RequestMapping("/getBoardList.do")
	public String getBoardList(BoardVO vo, Model model) {
		// Null Check
		if (vo.getSearchCondition() == null)
			vo.setSearchCondition("TITLE");
		if (vo.getSearchKeyword() == null)
			vo.setSearchKeyword("");
		// Model 정보 저장
		model.addAttribute("boardList", boardService.getBoardList(vo)); // Model 정보 저장
		return "getBoardList.jsp"; // View 이름 리턴
	}
}