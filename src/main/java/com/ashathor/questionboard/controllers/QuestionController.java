package com.ashathor.questionboard.controllers;

import com.ashathor.questionboard.models.Board;
import com.ashathor.questionboard.models.Question;
import com.ashathor.questionboard.models.User;
import com.ashathor.questionboard.repositories.BoardRepository;
import com.ashathor.questionboard.repositories.QuestionRepository;
import com.ashathor.questionboard.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private BoardRepository boardRepository;

    private QuestionRepository questionRepository;
    
    private UserRepository userRepository;

    public QuestionController(BoardRepository boardRepository, QuestionRepository questionRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
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
        User user = userRepository.findById(question.getUserId()).orElse(null);
        modelMap.put("question", question);
        modelMap.put("board", board);
        modelMap.put("user", user);
        return "question/question";
    }

    //try to get param name working
    public Board getClass(@RequestParam(value = "name") String name) {
        return boardRepository.findByTitle(name);
    }

    @GetMapping("/new/{id}")
    public String boardForm(@PathVariable("id") Integer boardId, Model model) {
        Question question = new Question();
        question.setBoardId(boardId);
        model.addAttribute("question", question);
        return "question/newquestion";
    }

    @PostMapping("/new/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String create(@PathVariable("id") Integer boardId, @ModelAttribute Question question, Model model) {
        question.setBoardId(boardId);
        question.setId(null);
        question.setVote(0);
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
        model.addAttribute("question", question);
        return "question/newsuccess";
    }

    @GetMapping("/delete")
    @Transactional
    public String delete(@RequestParam Long id, @RequestParam Long boardId) {
        questionRepository.deleteById(id);
        return "redirect:/board";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        Question question = questionRepository.getOne(id);
        question.setAnswerCreateDate(LocalDateTime.now());
        model.addAttribute("question", question);
        return "question/updateQuestion";
    }

    @PostMapping("/update/{id}")
    @Transactional
    public String doUpdate(@PathVariable("id") Long id, @ModelAttribute Question updatedQuestion, Model model) {
        Question oldQuestion = questionRepository.findById(id).orElse(null);
        oldQuestion.setAnswer(updatedQuestion.getAnswer());
        oldQuestion.setAnswerCreateDate(LocalDateTime.now());
        questionRepository.save(oldQuestion);
        //model.addAttribute("board", oldBoard);
        return "redirect:/question/{id}";
    }
    @GetMapping("/upvote/{id}")
    @Transactional
    public String upvote(@PathVariable("id") Long id, Model model) {
        Question question = questionRepository.getOne(id);
        int vote;
        if(question.getVote() == null){
            question.setVote(1);
        } else {
            vote = question.getVote() + 1;
            question.setVote(vote);
        }
        int boardId = question.getBoardId();
        model.addAttribute("boardId", boardId);
        questionRepository.save(question);
        return "redirect:/board";
    }

    @GetMapping("/downvote/{id}")
    @Transactional
    public String downvote(@PathVariable("id") Long id, ModelMap modelMap) {
        Question question = questionRepository.getOne(id);
        int vote;
        if(question.getVote() == null){
            question.setVote(-1);
        } else {
            vote = question.getVote() - 1;
            question.setVote(vote);
        }
        int boardId = question.getBoardId();
        modelMap.put("boardId", boardId);
        questionRepository.save(question);
        return "redirect:/board";
    }
}
