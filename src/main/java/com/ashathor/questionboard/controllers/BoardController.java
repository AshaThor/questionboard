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


    //When a user calls URL/board/{id} - {id} being a number variable the controller knows to send
    //that request here
    @GetMapping("/{id}")
    public String getById(@PathVariable("id") long id, ModelMap modelMap) {
        //Using the id given in the path we are able to look up the instance of the
        // board that is being requested from the database
        Board board = boardRepository.getOne(id);
        //Using the same information we can the find all the questions that are on the board
        //by creating a predefines sql query in this case a Get * When boardId = x
        List<Question> questionList = questionRepository.findAllByBoardId((int)id);
        //Now you are able to map the objects you have retrieved form the database and put
        //them into objetcs that the template engine can understand
        modelMap.put("board", board);
        modelMap.put("questionList", questionList);
        //The return statement is actually a path reference to the HTML template that this
        //request is to build and return as a view item aka src/main/resources/templates/board/board.html
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
