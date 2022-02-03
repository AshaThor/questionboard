package com.ashathor.questionboard.repositories;

import com.ashathor.questionboard.models.Question;
import com.ashathor.questionboard.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByBoardId(int id);
    Question findById(long id);
}
