package com.ashathor.questionboard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
/*
* This is a model class with alot of decorators, @Getter and @Setter auto generate getters and setters for all the
* variables given in the class. @entity tells Spring boot that this is an entity model.
* @Table is link to the database so that spring knows where the data map for this model is
*
* */
@Getter
@Setter
@Entity
@Table(name = "board", schema = "question_board")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Board {
    private static final Integer HOOK_LENGTH = 140;

    //the following decorators define how and where we are getting a new id. This defines a database sequence that is
    //used when making a new board.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_generator")
    @SequenceGenerator(name="board_generator", sequenceName = "board_id_seq", schema = "question_board")
    @Column(name="id", unique=true, nullable=false)
    private Long id;
    private String title;
    @Column(name="user_id")
    private Long userId;
}
