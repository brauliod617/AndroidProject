package com.duarte.androidproject2;

public class Questions {
    private String opEmail;
    private String questionTitle;
    private int numberOfComments;
    private String content;

    public Questions(String opEmail, String questionTitle, String content) {
        this.opEmail = opEmail;
        this.questionTitle = questionTitle;
        this.content = content;
        this.numberOfComments = 0;
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
}
