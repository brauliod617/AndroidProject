package com.duarte.androidproject2;

public class Questions {
    private String opUserName;
    private String questionTitle;
    private int numberOfComments;

    public Questions(String opUserName, String questionTitle, int numberOfComments) {
        this.opUserName = opUserName;
        this.questionTitle = questionTitle;
        this.numberOfComments = numberOfComments;
    }

    public Questions() {
    }

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
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
}
