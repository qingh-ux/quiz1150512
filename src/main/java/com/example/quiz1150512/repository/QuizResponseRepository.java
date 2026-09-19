package com.example.quiz1150512.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.quiz1150512.entity.QuizResponse;



@Repository
public interface QuizResponseRepository extends JpaRepository<QuizResponse,Long>{
	List<QuizResponse> findByQuizlistId(Long quizlistId);
}
