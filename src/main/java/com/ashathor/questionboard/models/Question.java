package com.ashathor.questionboard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * This is a model class with alot of decorators, @Getter and @Setter auto generate getters and setters for all the
 * variables given in the class. @entity tells Spring boot that this is an entity model.
 * @Table is link to the database so that spring knows where the data map for this model is
 *
 * */

@Getter
@Setter
@Entity
@Table(name = "question", schema = "question_board")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_generator")
    @SequenceGenerator(name="question_generator", sequenceName = "question_id_seq", schema = "question_board")
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    //Due to the naming convention difference each field that is cammel case has been changed to reflect the database column it represents
    @Column(name="board_id")
    private int boardId;
    private String title;
    @Column(name="user_id")
    private Long userId;
    private String description;
    @Column(name="create_date")
    private LocalDateTime createDate;
    @Column(name="update_date")
    //Nullable allows this filed to be set to null when handling data
    @Nullable
    private LocalDateTime dateUpdated;
    @Nullable
    private Integer vote;
    @Nullable
    @Column(nullable = true)
    private String answer;
    @Nullable
    @Column(name="answer_user_id")
    //Interger not int to avoid null primitive type issues
    private Integer answerUserId;
    @Nullable
    @Column(name="answer_create_date")
    private LocalDateTime answerCreateDate;
    @Nullable
    @Column(name="answer_update_date")
    private LocalDateTime answerUpdateDate;

}
