package com.example.quiz1150512.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.quiz1150512.entity.Quizlist;


@Repository
public interface QuizlistRepository extends JpaRepository<Quizlist,Long>{
//繼承JpaRepository就能使用所有方法
	
	// 支援標題模糊搜尋與日期區間篩選的查詢方法
    @Query("SELECT q FROM Quizlist q WHERE " +
           "(:title IS NULL OR q.title LIKE %:title%) AND " +
           "(:startDate IS NULL OR q.startDate >= :startDate) AND " +
           "(:endDate IS NULL OR q.endDate <= :endDate)")
    List<Quizlist> findByFilters(
        @Param("title") String title,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate
    );
}
