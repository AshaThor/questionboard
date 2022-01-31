package com.ashathor.questionboard.controllers;

import com.ashathor.questionboard.models.Board;
import com.ashathor.questionboard.models.Question;
import com.ashathor.questionboard.repositories.BoardRepository;
import com.ashathor.questionboard.repositories.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/board")
public class BoardController {

    private BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    //Root
    @GetMapping
    public String listClasses(ModelMap modelMap) {
        List<Board> boardList = boardRepository.findAll();
        modelMap.put("boardList", boardList);
        return "board/boards";
    }


    @GetMapping("/{id}")
    public String getById(@PathVariable("id") long id, ModelMap modelMap) {
        Board board = boardRepository.getOne(id);
        modelMap.put("board", board);
        return "board/board";
    }

    //try to get param name working
    public Board getClass(@RequestParam(value = "name") String name) {
        return boardRepository.findByTitle(name);
    }

    @GetMapping("/new")
    public String boardForm(Model model) {
        model.addAttribute("board", new Board());
        return "board/newboard";
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public String create(@ModelAttribute Board board, Model model) {
        model.addAttribute("board", board);
        boardRepository.save(board);
        return "board/newsuccess";
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@ModelAttribute Board board, Model model) {
        boardRepository.delete(board);
    }

    @GetMapping("/admin")
    public String rpgClassAdmin(ModelMap modelMap) {
        List<Board> boardList = boardRepository.findAll();
        modelMap.put("boardList", boardList);
        return "board/admin";
    }
}
