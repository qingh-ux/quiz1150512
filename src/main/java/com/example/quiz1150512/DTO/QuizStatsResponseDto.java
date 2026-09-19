package com.example.quiz1150512.DTO;

import java.util.List;

public class QuizStatsResponseDto {
    private Long quizId;
    private String quizTitle;
    private List<QuestionStatDto> questions;

    // Getters and Setters
    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }
    public String getQuizTitle() { return quizTitle; }
    public void setQuizTitle(String quizTitle) { this.quizTitle = quizTitle; }
    public List<QuestionStatDto> getQuestions() { return questions; }
    public void setQuestions(List<QuestionStatDto> questions) { this.questions = questions; }

    public static class QuestionStatDto {
        private Long questionId;
        private String questionText;
        private List<OptionStatDto> options;

        // Getters and Setters
        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }
        public String getQuestionText() { return questionText; }
        public void setQuestionText(String questionText) { this.questionText = questionText; }
        
        // 修正這裡：確保名稱與變數一致
        public List<OptionStatDto> getOptions() { return options; }
        public void setOptions(List<OptionStatDto> options) { this.options = options; }
    }

    public static class OptionStatDto {
        private String optionText;
        private long count;

        public OptionStatDto(String optionText, long count) {
            this.optionText = optionText;
            this.count = count;
        }

        public String getOptionText() { return optionText; }
        public void setOptionText(String optionText) { this.optionText = optionText; }
        public long getCount() { return count; }
        public void setCount(long count) { this.count = count; }
    }
}