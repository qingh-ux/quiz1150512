package com.example.quiz1150512.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "response_detail")
public class ResponseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✨ 對齊筆記：quiz_response_id
    @Column(name = "quiz_response_id")
    private Long responseId;

    // ✨ 對齊筆記：questionlist_id
    @Column(name = "questionlist_id")
    private Long questionlistId; // 👈 改成 questionlistId
    
 // ✨ 將原本對應 question_option_id 的欄位改為對應 answer_text
    @Column(name = "answer_text")
    private String selectedOptions; 
    // (或者妳也可以把變數名稱改得更貼切，例如 private String answerText;)

    // --- Getter & Setter ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

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