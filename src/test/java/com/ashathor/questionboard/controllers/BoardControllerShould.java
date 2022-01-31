
package com.ashathor.questionboard.controllers;

import com.ashathor.questionboard.models.Board;
import com.ashathor.questionboard.repositories.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ModelMap;

import java.util.List;
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

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks // auto inject helloRepository
    private BoardController boardController = new BoardController(boardRepository);

    @BeforeEach
    void setMockOutput() {
        ModelMap modelMap = new ModelMap();
        //when(boardController.listClasses(modelMap)).thenReturn("Hello Mockito From Repository");
    }

    @Test
    void bePresent() {
        assertNotNull("Board Controller is not present", boardController);
    }

    @Test
    void returnClassesListLink() {
        ModelMap modelMap = new ModelMap();
        assertEquals("Board list did not return correct link", "board/boards", boardController.listClasses(modelMap));
    }

    @Test
    void returnIndividualClassLink() {
        ModelMap modelMap = new ModelMap();
        assertEquals("An individual board did not return correctly return link", "board/board", boardController.getById(1, modelMap));
    }

    /*@Test
    void returnAdminLink() {
        ModelMap modelMap = new ModelMap();
        assertEquals("Admin link was not returned correctly", "board/admin", boardController.boardAdmin(modelMap));
    }*/

    @Test
    public void returnClassesGivesList() {
        List boardList = asList(new Board());
        when(boardRepository.findAll()).thenReturn(boardList);

        ModelMap modelMap = new ModelMap();
        String viewName = boardController.listClasses(modelMap);

        assertEquals("Root returned successfully", "board/boards", viewName);
        assertThat(modelMap, hasEntry("board", (Object) boardList));
    }
}

