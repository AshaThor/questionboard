package com.ashathor.questionboard.controllers;

import com.ashathor.questionboard.models.Board;
import com.ashathor.questionboard.models.Question;
import com.ashathor.questionboard.repositories.BoardRepository;
import com.ashathor.questionboard.repositories.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private BoardRepository boardRepository;

    private QuestionRepository questionRepository;

    public BoardController(BoardRepository boardRepository, QuestionRepository questionRepository) {
        this.boardRepository = boardRepository;
        this.questionRepository = questionRepository;
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
        List<Question> questionList = questionRepository.findAllByBoardId((int)id);
        modelMap.put("board", board);
        modelMap.put("questionList", questionList);
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

    @GetMapping("/deleteBoard")
    @Transactional
    public String delete(@RequestParam Long id) {
        boardRepository.deleteById(id);
        return "redirect:/board";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        Board board = boardRepository.getOne(id);
        model.addAttribute("board", board);
        return "board/updateBoard";
    }

    @PostMapping("/update/{id}")
    @Transactional
    public String doUpdate(@PathVariable("id") Long id, @ModelAttribute Board updatedBoard, Model model) {
        Board oldBoard = boardRepository.findById(id).orElse(null);
        oldBoard.setTitle(updatedBoard.getTitle());
        boardRepository.save(oldBoard);
        //model.addAttribute("board", oldBoard);
        return "redirect:/board";
    }
}
