package com.duarte.androidproject2;

import java.util.HashMap;

public class Answer {
    String opEmail;
    String answerContent;

    public Answer(String opEmail, String answerContent){
        this.opEmail = opEmail;
        this.answerContent = answerContent;
    }

    public Answer(HashMap<String, Object> objAnswers){
        this.opEmail = objAnswers.get("OPemail").toString();
        this.answerContent = objAnswers.get("content").toString();
    }

    public String getOpEmail() {
        return opEmail;
    }

    public String getAnswerContent() {
        return answerContent;
    }
}
