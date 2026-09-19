package com.example.quiz1150512.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz1150512.DTO.QuizDto; // ⚠️ 妹寶注意 package 大小寫
import com.example.quiz1150512.DTO.QuizStatsResponseDto;
import com.example.quiz1150512.entity.Quizlist;
import com.example.quiz1150512.service.QuizlistService;

@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequestMapping("/api/quiz")
public class QuizlistController {

	@Autowired
	private QuizlistService quizlistService;

	// 1. 取得所有問卷
	@GetMapping("/all")
	public List<Quizlist> getAllQuizzes() {
		return quizlistService.getAllQuizzes();
	}

	// 2. 新增單張問卷
	@PostMapping("/create")
	public Quizlist createQuiz(@RequestBody Quizlist quiz) {
		return quizlistService.createOrUpdateQuiz(quiz);
	}

	// 3. ✨【最新新增】一次新增整份問卷（含題目與選項）
	@PostMapping("/create-full")
	public Quizlist createFullQuiz(@RequestBody QuizDto dto) {
		return quizlistService.createFullQuiz(dto);
	}

	// 3. 刪除問卷 API
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteQuiz(@PathVariable("id") Long id) {
		try {
			quizlistService.deleteQuizById(id);
			return ResponseEntity.ok("問卷刪除成功！"); // 👈 這裡改成 ResponseEntity
		} catch (Exception e) {
			return ResponseEntity.status(500).body("刪除失敗：" + e.getMessage()); // 👈 這裡也是
		}
	}

	@PutMapping("/update")
	public Quizlist updateQuiz(@RequestBody QuizDto dto) {
		return quizlistService.updateQuiz(dto);
	}

	// 前台問卷列表搜尋與取得 API
	// 範例網址：http://localhost:8080/api/quiz/search?title=人氣&startDate=2023-01-01&endDate=2023-12-31
	@GetMapping("/search")
	public ResponseEntity<List<Quizlist>> searchQuizzes(
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "startDate", required = false) String startDate, 
			@RequestParam(value = "endDate", required = false) String endDate) {

		List<Quizlist> list = quizlistService.searchQuizzes(title, startDate, endDate);
		return ResponseEntity.ok(list);
	}

	// 5. 取得單張完整問卷詳情（供後端編輯使用）
	@GetMapping("/{id}")
	public QuizDto getQuizDetail(@PathVariable("id") Long id) { // 💡 加上 ("id") 明確指定！
		return quizlistService.getQuizDetailById(id);
	}
	
	@GetMapping("/stats/{id}")
	public ResponseEntity<com.example.quiz1150512.DTO.QuizStatsResponseDto> getQuizStats(@PathVariable("id") Long id) {
		com.example.quiz1150512.DTO.QuizStatsResponseDto stats = quizlistService.getQuizStats(id);
		return ResponseEntity.ok(stats);
	}
}