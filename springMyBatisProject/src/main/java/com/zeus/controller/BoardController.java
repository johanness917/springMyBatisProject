package com.zeus.controller;

import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zeus.domain.Board;
import com.zeus.service.BoardService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@MapperScan(basePackages = "com.zeus.mapper")
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@GetMapping("/insertForm")
	public String boardInsertForm(Model model) {
		return "board/insertForm";
	}

	@PostMapping("/insert")
	public String boardInsert(Board board, Model model) {
		log.info("insert board=" + board.toString());
		try {
			boardService.create(board);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "%s 님 게시판 등록 실패.".formatted(board.getWriter()));
			return "board/failed";
		}
		model.addAttribute("message", "%s 님의 게시판이 등록 되었습니다.".formatted(board.getWriter()));
		return "board/success";
	}

	@GetMapping("/boardList") // 오타 수정
	public String boardList(Model model) {
		log.info("boardList");
		try {
			List<Board> boardList = boardService.list();
			model.addAttribute("boardList", boardList); // 이름 일치 시킴
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "board/boardList"; // 실제 파일명과 매칭
	}

	@GetMapping("/detail") // 오타 수정
	public String boardDetail(Board b, Model model) {
		log.info("boardDetail board=" + b.toString());
		try {
			Board board = boardService.read(b);
			if (board == null) {
				model.addAttribute("message", "%d 님의 상세정보가 없습니다.".formatted(board.getNo()));
				return "board/failed";
			}
			model.addAttribute("board", board); // 이름 일치 시킴
		} catch (Exception e) {
		}
		return "board/detail"; // 실제 파일명과 매칭
	}

	@GetMapping("/delete") // 오타 수정
	public String boardDelete(Board board, Model model) {
		log.info("boardDelete board=" + board.toString());
		try {
			boardService.delete(board);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "%d 님의 정보가 삭제 실패.".formatted(board.getNo()));
			return "board/failed"; // 실제 파일명과 매칭
		}
		model.addAttribute("message", "%d 님의 정보가 삭제되었습니다.".formatted(board.getNo()));
		return "board/success";
	}

	@GetMapping("/updateForm") // 오타 수정
	public String boardUpdateForm(Board b, Model model) {
		log.info("boardUpdateForm board=" + b.toString());
		try {
			Board board = boardService.read(b);
			if (board == null) {
				model.addAttribute("message", "%d 님의 정보가 없습니다.".formatted(board.getNo()));
				return "board/failed";
			}
			model.addAttribute("board", board);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "board/updateForm"; // 실제 파일명과 매칭
	}

	@PostMapping("/update") // 오타 수정
	public String boardUpdate(Board b, Model model) {
		log.info("boardUpdate board=" + b.toString());
		try {
			boardService.update(b);
			model.addAttribute("board", b);
			model.addAttribute("message", "%s 게시판 수정 실패.".formatted(b.getWriter()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("message", "%s 게시판 수정 성공 했습니다.".formatted(b.getWriter()));
		return "board/success";
	}

	@GetMapping("/search") // 오타 수정
	public String boardSearch(Board board, Model model) {
		log.info("boardSearch board" + board.toString());
		try {
			List<Board> boardList = boardService.search(board);
			model.addAttribute("boardList", boardList); // 이름 일치 시킴
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "board/boardList"; // 실제 파일명과 매칭
	}

}