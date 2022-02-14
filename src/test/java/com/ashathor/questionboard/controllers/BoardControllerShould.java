
package com.ashathor.questionboard.controllers;

import com.ashathor.questionboard.models.Board;
import com.ashathor.questionboard.repositories.BoardRepository;
import com.ashathor.questionboard.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.assertj.core.util.Arrays.asList;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class BoardControllerShould {


/**
     * This method should be followed for mocking and testing all controllers from this application until such time
     * that we convert to REST interface
     * https://blog.trifork.com/2012/12/11/properly-testing-spring-mvc-controllers/
     */


    private static final Logger log = Logger.getLogger(BoardControllerShould.class.getName());

    //Mocking the data repo so that the database does not need to be connected
    @Mock
    private BoardRepository boardRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks // auto inject repos into the constructor
    private BoardController boardController = new BoardController(boardRepository, questionRepository);




    @BeforeEach
    void setMockOutput() {
        ModelMap modelMap = new ModelMap();
    }

    @Test
    void bePresent() {
        //Testing that the board controller is visible to the test
        assertNotNull("Board Controller is not present", boardController);
    }

    @Test
    // Tests list classes
    void returnBoardsListLink() {
        //Creates model map to be sent from the controller
        ModelMap modelMap = new ModelMap();
        //This assesses if he string passed from the controller to the template engine is the correct string and if not
        //the message will be shown
        //We also inject a dummy model map so that the method can resolve
        assertEquals("Board list did not return correct link", "board/boards", boardController.listClasses(modelMap));
    }

    @Test
    // Tests get by id
    void returnIndividualBoardLink() {
        ModelMap modelMap = new ModelMap();
        assertEquals("An individual board did not return correctly return link", "board/board", boardController.getById(1, modelMap));
    }

}

