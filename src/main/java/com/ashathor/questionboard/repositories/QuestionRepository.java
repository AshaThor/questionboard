package com.ashathor.questionboard.repositories;

import com.ashathor.questionboard.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
