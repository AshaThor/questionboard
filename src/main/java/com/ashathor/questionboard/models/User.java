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
@Table(name = "user", schema = "question_board")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    private static final Integer HOOK_LENGTH = 140;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name="user_generator", sequenceName = "user_id_seq", schema = "question_board")
    @Column(name="id", unique=true, nullable=false)
    private Long id;
    private String username;
    private int type;
}
