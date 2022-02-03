package com.ashathor.questionboard.repositories;

import com.ashathor.questionboard.models.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByTitle(String title);
    Board findById(long id);
    //Board deleteBoardById(Long id);
}
