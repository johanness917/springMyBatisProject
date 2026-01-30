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
			int count = boardService.create(board);
			if (count > 0) {
				model.addAttribute("message", "%s 님의 게시판이 등록 되었습니다.".formatted(board.getWriter()));
				return "board/success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("message", "%s 님 게시판 등록 실패.".formatted(board.getWriter()));
		return "board/failed";
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

	@GetMapping("/delete")
	public String boardDelete(Board board, Model model) {
	    log.info("boardDelete board=" + board.toString());
	    try {
	        int count = boardService.delete(board);
	        
	        // count가 1 이상이면 데이터가 정상적으로 삭제된 것입니다.
	        if (count > 0) {
	            model.addAttribute("message", "%d번 게시글이 삭제되었습니다.".formatted(board.getNo()));
	            return "board/success";
	        } 
	    } catch (Exception e) {
	        log.error("삭제 중 오류 발생", e);
	    }
	    model.addAttribute("message", "삭제 처리 중 시스템 오류가 발생했습니다.");
	    return "board/failed";
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

	@PostMapping("/update")
	public String boardUpdate(Board b, Model model) {
	    log.info("boardUpdate board=" + b.toString());
	    try {
	        int count = boardService.update(b);
	        
	        // count가 1이면 성공, 0이면 수정된 행이 없으므로 실패입니다.
	        if (count > 0) {
	            model.addAttribute("message", "%s님의 게시글이 성공적으로 수정되었습니다.".formatted(b.getWriter()));
	            return "board/success";
	        }
	    } catch (Exception e) {
	        log.error("수정 중 오류 발생: ", e);
	    }
	    model.addAttribute("message", "시스템 오류가 발생했습니다.");
	    return "board/failed";
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