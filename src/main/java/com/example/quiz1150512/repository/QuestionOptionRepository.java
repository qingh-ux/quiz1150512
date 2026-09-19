package com.example.quiz1150512.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.quiz1150512.entity.QuestionOption;

import jakarta.transaction.Transactional;


@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOption,Long>{
	List<QuestionOption> findByQuestionId(Long questionId);
	@Transactional
	void deleteByQuestionId(Long questionId);
}
