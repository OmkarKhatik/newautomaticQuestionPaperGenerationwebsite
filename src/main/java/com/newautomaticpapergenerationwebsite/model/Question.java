package com.newautomaticpapergenerationwebsite.model;

import jakarta.persistence.*;

@Entity

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String questionText;		// text
    private String difficulty;			// easy, medium, hard, all
    private String module;				// 1, 2, 3
    private String semester;			// 1, 2, 3
    private String subject;				// java
    private String branch;				// Computer Technology
    private String topic;				// basic, oops
    private String questionType;		// mcq, descriptive, both

    public Question(Long id, String questionText, String difficulty, String module, String semester, String subject, String branch, String topic, String questionType) {
        this.id = id;
        this.questionText = questionText;
        this.difficulty = difficulty;
        this.module = module;
        this.semester = semester;
        this.subject = subject;
        this.branch = branch;
        this.topic = topic;
        this.questionType = questionType;
    }

    public Question() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
