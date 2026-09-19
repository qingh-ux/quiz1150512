package com.example.quiz1150512.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.quiz1150512.entity.ResponseDetail;


@Repository
public interface ResponseDetailRepository extends JpaRepository<ResponseDetail,Long>{
	List<ResponseDetail> findByResponseIdIn(List<Long> responseIds);
	long countByQuestionlistIdAndSelectedOptions(Long questionlistId, String selectedOptions);
}
