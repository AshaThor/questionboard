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
//URL decorator, the controller will auto route any URL/question requests here.
@RequestMapping("/question")
public class QuestionController {

    private BoardRepository boardRepository;

    private QuestionRepository questionRepository;
    
    private UserRepository userRepository;

    //Auto injectiing the data handing into the constructor so that the constructor has visibility of the database calls
    //it requires to perform tasks in the CRUD methods.
    public QuestionController(BoardRepository boardRepository, QuestionRepository questionRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    //Returns a Question view based on the question requested in the url, contains the board question and user,
    // all items required to build the view.
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

    //Returns a form to create a new question. The id required is that of the parent board in order to
    // accommodate the two.
    @GetMapping("/new/{id}")
    public String questionForm(@PathVariable("id") Integer boardId, Model model) {
        Question question = new Question();
        question.setBoardId(boardId);
        model.addAttribute("question", question);
        return "question/newquestion";
    }

    //Post request handler for a new question, this accepts the new question object as well as a path variable for the board id
    @PostMapping("/new/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String create(@PathVariable("id") Integer boardId, @ModelAttribute Question question, Model model) {
        //due to some unforseen issues the id was being set in the ui and this is not inkeeping wioth how the application
        //assigns id's therefore a reset here is required.
        question.setBoardId(boardId);
        question.setId(null);
        question.setVote(0);
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
        model.addAttribute("question", question);
        return "question/newsuccess";
    }

    //Deletes the requested question and redirects the user to the boards view
    @GetMapping("/delete")
    @Transactional
    public String delete(@RequestParam Long id, @RequestParam Long boardId) {
        questionRepository.deleteById(id);
        return "redirect:/board";
    }

    //Returns a form for updating the question of id given in the request
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        Question question = questionRepository.getOne(id);
        question.setAnswerCreateDate(LocalDateTime.now());
        model.addAttribute("question", question);
        return "question/updateQuestion";
    }

    //Accepts a post request of the updated question of given question in the request path.
    @PostMapping("/update/{id}")
    @Transactional
    public String doUpdate(@PathVariable("id") Long id, @ModelAttribute Question updatedQuestion, Model model) {
        Question oldQuestion = questionRepository.findById(id).orElse(null);
        oldQuestion.setAnswer(updatedQuestion.getAnswer());
        oldQuestion.setAnswerCreateDate(LocalDateTime.now());
        questionRepository.save(oldQuestion);
        return "redirect:/question/{id}";
    }

    //This handles a vote request to add one to the vote number and redirects back to boards. This is not ideal howevr
    //is a working solution
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
