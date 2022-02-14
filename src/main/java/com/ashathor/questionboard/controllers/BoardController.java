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
//URL decorator, the controller will auto route any URL/board requests here.
@RequestMapping("/board")
public class BoardController {

    private BoardRepository boardRepository;

    private QuestionRepository questionRepository;

    //Auto injectiing the data handing into the constructor so that the constructor has visibility of the database calls
    //it requires to perform tasks in the CRUD methods.
    public BoardController(BoardRepository boardRepository, QuestionRepository questionRepository) {
        this.boardRepository = boardRepository;
        this.questionRepository = questionRepository;
    }

    //Root
    //Returns a full list of all the boards in the application
    @GetMapping
    public String listClasses(ModelMap modelMap) {
        List<Board> boardList = boardRepository.findAll();
        modelMap.put("boardList", boardList);
        return "board/boards";
    }


    //Returns a board given an id and returns the questions that are related to that board as a list of questions
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

    //Returns a form page to create a new board
    @GetMapping("/new")
    public String boardForm(Model model) {
        model.addAttribute("board", new Board());
        return "board/newboard";
    }

    //Accepts a post request and then adds the given board object to the database
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public String create(@ModelAttribute Board board, Model model) {
        model.addAttribute("board", board);
        boardRepository.save(board);
        return "board/newsuccess";
    }

    //Method takes an id of a baord that is being deleted, performs the delete and then returns a redirect call to update
    //the users view with the changes
    @GetMapping("/deleteBoard")
    @Transactional
    public String delete(@RequestParam Long id) {
        boardRepository.deleteById(id);
        return "redirect:/board";
    }

    //Returns a form so that the user can update their given question fields based on the id provided.
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        Board board = boardRepository.getOne(id);
        model.addAttribute("board", board);
        return "board/updateBoard";
    }

    //Accepts a post request with an updated object of board to the push to the database and override the existing entry
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
