package com.ashathor.questionboard.repositories;

import com.ashathor.questionboard.models.Question;
import com.ashathor.questionboard.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findByBoardId(Long id);
}
