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
@RequestMapping("/question")
public class QuestionController {

    private BoardRepository boardRepository;

    private QuestionRepository questionRepository;

    public QuestionController(BoardRepository boardRepository, QuestionRepository questionRepository) {
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
        Question question = questionRepository.findById(id);
        Board board = boardRepository.getOne((long)question.getBoardId());
        modelMap.put("question", question);
        modelMap.put("board", board);
        return "question/question";
    }

    //try to get param name working
    public Board getClass(@RequestParam(value = "name") String name) {
        return boardRepository.findByTitle(name);
    }

    @GetMapping("/new")
    public String boardForm(Model model) {
        model.addAttribute("question", new Question());
        return "question/newquestion";
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public String create(@RequestParam Long id,@ModelAttribute Board board, Model model) {
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

    @GetMapping("/admin")
    public String rpgClassAdmin(ModelMap modelMap) {
        List<Board> boardList = boardRepository.findAll();
        modelMap.put("boardList", boardList);
        return "board/admin";
    }
}
