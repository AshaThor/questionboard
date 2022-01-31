package com.ashathor.questionboard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "board", schema = "question_board")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Board {
    private static final Integer HOOK_LENGTH = 140;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_generator")
    @SequenceGenerator(name="board_generator", sequenceName = "board_id_seq", schema = "question_board")
    @Column(name="id", unique=true, nullable=false)
    private Long id;
    private String title;
    @Column(name="user_id")
    private Long userId;
}
