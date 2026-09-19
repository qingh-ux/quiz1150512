package com.example.quiz1150512.DTO; // ⚠️ 請確認這行 package 名稱跟妹寶左邊資料夾一致（DTO 或 dto 或 service）

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizDto {
	private Long id; // 💡 確保有這個欄位！
    private String title;
    private String description;
    private List<QuestionDto> questions;
 // 💡 確保有這兩個欄位（型態通常是 String 或 Date，看妳原本資料庫怎麼設計的）
    private String startDate; 
    private String endDate;

    // --- 1. 外層 QuizDto 的 Getter / Setter ---
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<QuestionDto> getQuestions() { return questions; }
    public void setQuestions(List<QuestionDto> questions) { this.questions = questions; }
    
 // --- 記得一定要有這幾個 Getter 與 Setter ---
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

 // --- 記得要有 id 的 Getter 與 Setter ---
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    // --- 2. 內部類別：題目包裹 QuestionDto ---
    public static class QuestionDto {
    	@JsonProperty("title") // 👈 關鍵！這行告訴 Java：前端傳的 title 請存進這裡
        private String questionText;
    	private int questionNum; // 👈 加上這行接收題號
    	private String type; // 👈 接收題型
        private List<OptionDto> options;

        

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getQuestionText() { return questionText; }
        public void setQuestionText(String questionText) { this.questionText = questionText; }

        public int getQuestionNum() { return questionNum; }         // 👈 2. 補上這個 Getter
        public void setQuestionNum(int questionNum) { this.questionNum = questionNum; } // 👈 3. 補上這個 Setter
        
        public List<OptionDto> getOptions() { return options; }
        public void setOptions(List<OptionDto> options) { this.options = options; }
    }


    // --- 3. 內部類別：選項包裹 OptionDto ---
    public static class OptionDto {
        private String optionText;

        public String getOptionText() { return optionText; }
        public void setOptionText(String optionText) { this.optionText = optionText; }
    }
}