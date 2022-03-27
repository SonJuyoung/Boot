package com.juyoung.boot.service;

import com.juyoung.boot.model.Board;
import com.juyoung.boot.model.User;
import com.juyoung.boot.repository.BoardRepository;
import com.juyoung.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board) {
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
