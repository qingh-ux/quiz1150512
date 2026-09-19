package com.example.quiz1150512.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz1150512.DTO.FillQuizDto;
import com.example.quiz1150512.service.FillQuizService;

@CrossOrigin // 允許前端連進來！
@RestController
@RequestMapping("/api/fill")
public class FillQuizController {

	@Autowired
	private FillQuizService fillQuizService;

	// 前台使用者提交問卷答案 API
	@PostMapping("/submit")
	public String submitQuiz(@RequestBody FillQuizDto dto) {
		// 💡 印出前端傳過來的整包 DTO，抓出到底哪裡是 null
		System.out.println("🔥 收到前端傳來的 DTO 物件：" + dto);
		if (dto != null && dto.getAnswers() != null) {
			for (int i = 0; i < dto.getAnswers().size(); i++) {
				var ans = dto.getAnswers().get(i);
				System.out.println("👉 第 " + (i + 1) + " 筆答案的 questionlistId = " + ans.getQuestionlistId() + ", 選項 = " + ans.getSelectedOptions());
			}
		}

		return fillQuizService.submitQuiz(dto);
	}
}