package com.example.quiz1150512.DTO; // ⚠️ 妹寶請確認 Package 名稱大小寫與資料夾一致

import java.util.List;

public class FillQuizDto {

	// 填答者基本資料
	private String name;
	private String phone;
	private String email;
	private Integer age;

	// 問卷 ID (改為 Long 型態)
	private Long quizId;

	// 填答細節清單
	private List<AnswerDetailDto> answers;

	// --- Getter & Setter ---
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public List<AnswerDetailDto> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerDetailDto> answers) {
		this.answers = answers;
	}

	// 內部類別：答案細節
		public static class AnswerDetailDto {
			private Long questionlistId; // 💡 確保這裡的變數名稱叫 questionlistId
			private String selectedOptions;

			// 💡 必須要有這個 Getter
			public Long getQuestionlistId() {
				return questionlistId;
			}

			public void setQuestionlistId(Long questionlistId) {
				this.questionlistId = questionlistId;
			}

			public String getSelectedOptions() {
				return selectedOptions;
			}

			public void setSelectedOptions(String selectedOptions) {
				this.selectedOptions = selectedOptions;
			}
		}
}