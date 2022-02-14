package com.ashathor.questionboard.repositories;

import com.ashathor.questionboard.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This is the data access layer in the application. Spring gives us access to predefined SQL based on implemented methods in this interface,
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByBoardId(int id);

    Question findById(long id);
}
