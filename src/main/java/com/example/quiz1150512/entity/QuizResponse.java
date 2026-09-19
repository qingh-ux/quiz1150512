package com.example.quiz1150512.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "quiz_response")
public class QuizResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✨ 對齊筆記：quizlist_id
    @Column(name = "quizlist_id")
    private Long quizlistId;

    // ✨ 對齊筆記：userdata_email
    @Column(name = "userdata_email")
    private String userdataEmail;

    // ✨ 對齊筆記：submitted_at
    @Column(name = "submitted_at")
    private LocalDateTime fillTime;

    // --- Getter & Setter ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuizlistId() {
        return quizlistId;
    }

    public void setQuizlistId(Long quizlistId) {
        this.quizlistId = quizlistId;
    }

    public String getUserdataEmail() {
        return userdataEmail;
    }

    public void setUserdataEmail(String userdataEmail) {
        this.userdataEmail = userdataEmail;
    }

    public LocalDateTime getFillTime() {
        return fillTime;
    }

    public void setFillTime(LocalDateTime fillTime) {
        this.fillTime = fillTime;
    }
}