package com.duarte.androidproject2;

import java.util.HashMap;

public class Questions {
    private String opEmail;
    private String questionTitle;
    private int numberOfComments;
    private String content;
    private String classOfQuestion;

    public Questions(String opEmail, String questionTitle, String content, String classOfQuestion) {
        this.opEmail = opEmail;
        this.questionTitle = questionTitle;
        this.content = content;
        this.numberOfComments = 0;
        this.classOfQuestion = classOfQuestion;
    }

    public Questions(HashMap<String, Object> objQuestions){
        this.opEmail = objQuestions.get("opEmail").toString();
        this.questionTitle = objQuestions.get("questionTitle").toString();
        this.content = objQuestions.get("content").toString();
        this.numberOfComments = 0;
        this.classOfQuestion = objQuestions.get("classOfQuestion").toString();
    }

    public Questions() {
    }

    public String getOpEmail() {
        return opEmail;
    }

    public void setOpEmail(String opEmail) {
        this.opEmail = opEmail;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassOfQuestion() {
        return classOfQuestion;
    }
}
