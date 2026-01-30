package com.zeus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeus.domain.Board;
import com.zeus.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardMapper mapper;

	@Override
	@Transactional
	public void create(Board board) throws Exception {
		mapper.create(board);
		;
	}

	@Override
	@Transactional(readOnly = true)
	public Board read(Board board) throws Exception {
		return mapper.read(board.getNo());
	}

	@Override
	@Transactional
	public void update(Board board) throws Exception {
		mapper.update(board);
	}

	@Override
	public void delete(Board board) throws Exception {
		mapper.delete(board.getNo());
	}

	@Override
	public List<Board> list() throws Exception {
		return mapper.list();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Board> search(Board board) throws Exception {
		return mapper.search(board);
	}

}
