package com.example.quiz1150512.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz1150512.DTO.FillQuizDto;
import com.example.quiz1150512.entity.QuizResponse;
import com.example.quiz1150512.entity.ResponseDetail;
import com.example.quiz1150512.entity.Userdata;
import com.example.quiz1150512.repository.QuizResponseRepository;
import com.example.quiz1150512.repository.ResponseDetailRepository;
import com.example.quiz1150512.repository.UserdataRepository;

@Service
public class FillQuizService {

	@Autowired
	private UserdataRepository userdataRepository;

	@Autowired
	private QuizResponseRepository quizResponseRepository;

	@Autowired
	private ResponseDetailRepository responseDetailRepository;

	@Transactional
	public String submitQuiz(FillQuizDto dto) {
		// 1. 儲存或取得填答者個人資料 (Userdata) - 透過手機號碼查詢
				Userdata savedUser = userdataRepository.findByPhone(dto.getPhone()).orElse(null);
				
				if (savedUser == null) {
					// 如果資料庫還沒有這個人，才新建一筆
					Userdata user = new Userdata();
					user.setName(dto.getName());
					user.setPhone(dto.getPhone());
					user.setEmail(dto.getEmail());
					user.setAge(dto.getAge());
					savedUser = userdataRepository.save(user);
				}

		// 2. 建立填答總紀錄 (QuizResponse) - ✨ 對齊筆記欄位
		QuizResponse response = new QuizResponse();
		response.setQuizlistId(dto.getQuizId());             // 對應 quizlist_id
		response.setUserdataEmail(savedUser.getEmail());      // 對應 userdata_email (存個人資料的 email)
		response.setFillTime(LocalDateTime.now());           // 對應 submitted_at
		QuizResponse savedResponse = quizResponseRepository.save(response);

		// 3. 逐筆儲存詳細答案 (ResponseDetail)
		if (dto.getAnswers() != null && !dto.getAnswers().isEmpty()) {
			for (FillQuizDto.AnswerDetailDto answerDto : dto.getAnswers()) {
				ResponseDetail detail = new ResponseDetail();
				detail.setResponseId(savedResponse.getId()); // 綁定總紀錄 ID
				detail.setQuestionlistId(answerDto.getQuestionlistId());
				detail.setSelectedOptions(answerDto.getSelectedOptions());

				responseDetailRepository.save(detail);
			}
		}

		return "填答成功！感謝您的參與。";
	}
}