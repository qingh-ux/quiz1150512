package com.example.quiz1150512.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "questionlist") // ⚠️ 如果妹寶 MySQL 的表名是 questionlist，請改成 "questionlist"
public class Questionlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quizlist_id")
    private Long quizId;

    @Column(name = "title")
    private String questionText;

    @Column(name = "question_num") // 👈 加上這行對應資料庫的欄位
    private int questionNum;        // 👈 加上這個變數
    
    @Column(name = "type")
    private String type; // 👈 欄位變數

    public String getType() { 
        return type; 
    }

    public void setType(String type) { 
        this.type = type; 
    }
 // --- 記得補上 Getter 跟 Setter ---
    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }
    
    // --- 以下是自動包好的 Getter 和 Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
}