package com.example.quiz1150512.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz1150512.DTO.QuizDto;
import com.example.quiz1150512.DTO.QuizStatsResponseDto;
import com.example.quiz1150512.entity.QuestionOption;
import com.example.quiz1150512.entity.Questionlist;
import com.example.quiz1150512.entity.QuizResponse;
import com.example.quiz1150512.entity.Quizlist;
import com.example.quiz1150512.entity.ResponseDetail;
import com.example.quiz1150512.repository.QuestionOptionRepository;
import com.example.quiz1150512.repository.QuestionlistRepository;
import com.example.quiz1150512.repository.QuizResponseRepository;
import com.example.quiz1150512.repository.QuizlistRepository;
import com.example.quiz1150512.repository.ResponseDetailRepository;

import jakarta.transaction.Transactional;

@Service
public class QuizlistService {
	// 使用Autowired把Repository黏進來
	@Autowired
	private QuizlistRepository quizlistRepository;

//商業邏輯方法:取得所有問卷列表
	public List<Quizlist> getAllQuizzes() {
		return quizlistRepository.findAll();
	}

//商業邏輯方法:新增或修改一份問卷
	public Quizlist createOrUpdateQuiz(Quizlist quiz) {
		return quizlistRepository.save(quiz);
	}

//如果有其他 Repository 需要用來刪除子表格，也可以在這裡注入

	public void deleteQuizById(Long quizId) {
		// 1. 檢查該問卷是否存在
				if (!quizlistRepository.existsById(quizId)) {
					throw new RuntimeException("找不到該筆問卷資料！");
				}

				// 2. 找出該問卷底下的所有題目
				List<Questionlist> questions = questionlistRepository.findByQuizId(quizId);
				if (questions != null) {
					for (Questionlist q : questions) {
						// 3. 刪除該題目底下的所有選項
						questionOptionRepository.deleteByQuestionId(q.getId());
					}
					// 4. 刪除所有題目
					questionlistRepository.deleteAll(questions);
				}

				// 5. 最後安全刪除問卷主檔
				quizlistRepository.deleteById(quizId);
			}

	@Autowired
	private QuestionlistRepository questionlistRepository;

	@Autowired
	private QuestionOptionRepository questionOptionRepository;

// 一次存入問卷、題目與選項（加上 @Transactional 確保失敗時會全部 rollback 退回！）
	@Transactional
	public Quizlist createFullQuiz(QuizDto dto) {
		// 1. 先存問卷主表
		Quizlist quiz = new Quizlist();
		quiz.setTitle(dto.getTitle());
		quiz.setDescription(dto.getDescription());

		// 💡 關鍵：把前端傳過來的日期存進資料庫！
				// (因為 Entity 是 LocalDateTime，如果 DTO 是 String，需要轉型)
				if (dto.getStartDate() != null && !dto.getStartDate().isEmpty()) {
					quiz.setStartDate(java.time.LocalDateTime.parse(dto.getStartDate() + "T00:00:00"));
				}
				if (dto.getEndDate() != null && !dto.getEndDate().isEmpty()) {
					quiz.setEndDate(java.time.LocalDateTime.parse(dto.getEndDate() + "T00:00:00"));
				}

				Quizlist savedQuiz = quizlistRepository.save(quiz);
		
		// 2. 迴圈存每一題 Question
		if (dto.getQuestions() != null) {
			for (QuizDto.QuestionDto qDto : dto.getQuestions()) {
				Questionlist question = new Questionlist();
				question.setQuizId(savedQuiz.getId()); // 綁定問卷 ID (FK)
				question.setQuestionText(qDto.getQuestionText());
				question.setQuestionNum(qDto.getQuestionNum());
				question.setType(qDto.getType()); // 👈 加上這行把題型設定進去！
				Questionlist savedQuestion = questionlistRepository.save(question);

				// 3. 迴圈存該題目的每一個選項 Option
				if (qDto.getOptions() != null) {
					for (QuizDto.OptionDto oDto : qDto.getOptions()) {
						QuestionOption option = new QuestionOption();
						option.setQuestionId(savedQuestion.getId()); // 綁定題目 ID (FK)
						option.setOptionText(oDto.getOptionText());
						questionOptionRepository.save(option);
					}
				}
			}
		}
		return savedQuiz;
	}
	
	// 🔄 編輯更新問卷 API
		@Transactional
		public Quizlist updateQuiz(QuizDto dto) {
			// 1. 確保 DTO 裡面有帶 ID，才能進行更新而不是新增
			if (dto.getId() == null) {
				throw new RuntimeException("更新問卷時缺少 ID！");
			}

			// 2. 先把該問卷舊有的題目與選項全部刪除（避免重複累積）
			List<Questionlist> oldQuestions = questionlistRepository.findByQuizId(dto.getId());
			if (oldQuestions != null) {
				for (Questionlist q : oldQuestions) {
					questionOptionRepository.deleteByQuestionId(q.getId());
				}
				questionlistRepository.deleteAll(oldQuestions);
			}

			// 3. 取得原本的主檔
			Quizlist quiz = quizlistRepository.findById(dto.getId())
					.orElseThrow(() -> new RuntimeException("找不到要更新的問卷！"));

			quiz.setTitle(dto.getTitle());
			quiz.setDescription(dto.getDescription());

			// 4. 更新開始與結束日期
			if (dto.getStartDate() != null && !dto.getStartDate().isEmpty()) {
				quiz.setStartDate(java.time.LocalDateTime.parse(dto.getStartDate() + "T00:00:00"));
			}
			if (dto.getEndDate() != null && !dto.getEndDate().isEmpty()) {
				quiz.setEndDate(java.time.LocalDateTime.parse(dto.getEndDate() + "T00:00:00"));
			}

			Quizlist savedQuiz = quizlistRepository.save(quiz);

			// 5. 重新存入前端傳過來的最新題目與選項
			if (dto.getQuestions() != null) {
				for (QuizDto.QuestionDto qDto : dto.getQuestions()) {
					Questionlist question = new Questionlist();
					question.setQuizId(savedQuiz.getId());
					question.setQuestionText(qDto.getQuestionText());
					question.setQuestionNum(qDto.getQuestionNum());
					question.setType(qDto.getType());
					Questionlist savedQuestion = questionlistRepository.save(question);

					if (qDto.getOptions() != null) {
						for (QuizDto.OptionDto oDto : qDto.getOptions()) {
							QuestionOption option = new QuestionOption();
							option.setQuestionId(savedQuestion.getId());
							option.setOptionText(oDto.getOptionText());
							questionOptionRepository.save(option);
						}
					}
				}
			}
			return savedQuiz;
		}

	// 💡 根據 ID 取得完整的問卷資料（包含題目與選項，供後端編輯使用）
	public QuizDto getQuizDetailById(Long quizId) {
		Quizlist quiz = quizlistRepository.findById(quizId).orElseThrow(() -> new RuntimeException("找不到該問卷"));

		QuizDto dto = new QuizDto();
		dto.setTitle(quiz.getTitle());
		dto.setDescription(quiz.getDescription());

		// 💡 關鍵：把 LocalDateTime 轉成 String 塞進 DTO（避免型態不符報錯）
		// 這裡使用 toString() 或簡單截取前 10 碼（YYYY-MM-DD）給前端的 <input type="date"> 使用
		if (quiz.getStartDate() != null) {
			dto.setStartDate(quiz.getStartDate().toString().substring(0, 10));
		}
		if (quiz.getEndDate() != null) {
			dto.setEndDate(quiz.getEndDate().toString().substring(0, 10));
		}

		// 找出該問卷底下的所有題目
		List<Questionlist> questions = questionlistRepository.findByQuizId(quizId);
		List<QuizDto.QuestionDto> qDtoList = new java.util.ArrayList<>();

		if (questions != null) {
			for (Questionlist q : questions) {
				QuizDto.QuestionDto qDto = new QuizDto.QuestionDto();
				qDto.setQuestionNum(q.getQuestionNum());
				qDto.setQuestionText(q.getQuestionText());
				qDto.setType(q.getType());

				// 找出該題目底下的所有選項
				List<QuestionOption> options = questionOptionRepository.findByQuestionId(q.getId());
				List<QuizDto.OptionDto> oDtoList = new java.util.ArrayList<>();

				if (options != null) {
					for (QuestionOption o : options) {
						QuizDto.OptionDto oDto = new QuizDto.OptionDto();
						oDto.setOptionText(o.getOptionText());
						// 如果妳的 OptionDto 還有 optionCode，也可以在這裡加上 oDto.setOptionCode(o.getOptionCode());
						oDtoList.add(oDto);
					}
				}
				qDto.setOptions(oDtoList);
				qDtoList.add(qDto);
			}
		}
		dto.setQuestions(qDtoList);
		return dto;
	}

	// 根據標題與日期區間搜尋問卷列表
	public List<Quizlist> searchQuizzes(String title, String startDate, String endDate) {
		// 如果前端傳進來的字串是空字串，把它轉成 null 讓 SQL 條件略過它
		if (title != null && title.trim().isEmpty()) {
			title = null;
		}
		if (startDate != null && startDate.trim().isEmpty()) {
			startDate = null;
		}
		if (endDate != null && endDate.trim().isEmpty()) {
			endDate = null;
		}

		return quizlistRepository.findByFilters(title, startDate, endDate);
	}
	
	@Autowired
	private QuizResponseRepository quizResponseRepository;

	@Autowired
	private ResponseDetailRepository responseDetailRepository;

	// 📊 取得某張問卷的真實統計數據
		public com.example.quiz1150512.DTO.QuizStatsResponseDto getQuizStats(Long quizId) {
			Quizlist quiz = quizlistRepository.findById(quizId).orElseThrow(() -> new RuntimeException("找不到該問卷"));

			List<QuizResponse> responses = quizResponseRepository.findByQuizlistId(quizId);
			List<Long> responseIds = responses.stream().map(QuizResponse::getId).toList();

			List<ResponseDetail> allDetails = new java.util.ArrayList<>();
			if (!responseIds.isEmpty()) {
				allDetails = responseDetailRepository.findByResponseIdIn(responseIds);
			}

			com.example.quiz1150512.DTO.QuizStatsResponseDto statsDto = new com.example.quiz1150512.DTO.QuizStatsResponseDto();
			statsDto.setQuizId(quiz.getId());
			statsDto.setQuizTitle(quiz.getTitle());

			List<com.example.quiz1150512.DTO.QuizStatsResponseDto.QuestionStatDto> questionStats = new java.util.ArrayList<>();

			List<Questionlist> questions = questionlistRepository.findByQuizId(quizId);
			if (questions != null) {
				for (Questionlist q : questions) {
					com.example.quiz1150512.DTO.QuizStatsResponseDto.QuestionStatDto qStat = new com.example.quiz1150512.DTO.QuizStatsResponseDto.QuestionStatDto();
					qStat.setQuestionId(q.getId());
					qStat.setQuestionText(q.getQuestionText());

					List<com.example.quiz1150512.DTO.QuizStatsResponseDto.OptionStatDto> optionStats = new java.util.ArrayList<>();
					List<QuestionOption> options = questionOptionRepository.findByQuestionId(q.getId());

					if (options != null) {
						for (QuestionOption opt : options) {
							long count = 0;
							for (ResponseDetail detail : allDetails) {
								if (detail.getQuestionlistId() != null && 
									detail.getQuestionlistId().equals(q.getId()) && 
									detail.getSelectedOptions() != null && 
									opt.getOptionText() != null &&
									detail.getSelectedOptions().trim().equals(opt.getOptionText().trim())) {
									count++;
								}
							}
							optionStats.add(new com.example.quiz1150512.DTO.QuizStatsResponseDto.OptionStatDto(opt.getOptionText(), count));
						}
					}
					qStat.setOptions(optionStats);
					questionStats.add(qStat);
				}
			}

			statsDto.setQuestions(questionStats);
			return statsDto;
		}
	
}
