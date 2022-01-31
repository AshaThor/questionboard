package com.ashathor.questionboard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "question", schema = "question_board")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Question {
    /*Entity class for an RPG Character
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_generator")
    @SequenceGenerator(name="question_generator", sequenceName = "question_id_seq", schema = "question_board")
    @Column(name="id", unique=true, nullable=false)
    private Long id;
    @Column(name="board_id")
    private int boardId;
    private String title;
    @Column(name="user_id")
    private int userId;
    private String description;
    @Column(name="create_date")
    private LocalDateTime createDate;
    @Column(name="update_date")
    private LocalDateTime dateUpdated;
    private String answer;
    @Column(name="answer_user_id")
    private int answerUserId;
    @Column(name="answer_create_date")
    private LocalDateTime answerCreateDate;
    @Column(name="answer_update_date")
    private LocalDateTime answerUpdateDate;

}
