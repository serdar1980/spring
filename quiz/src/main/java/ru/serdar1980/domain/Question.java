package ru.serdar1980.domain;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String question;
    private List<Answer> answers = new ArrayList<>();

    public Question(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return new ArrayList<Answer>(answers);
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = new ArrayList<Answer>(answers);
    }
}
