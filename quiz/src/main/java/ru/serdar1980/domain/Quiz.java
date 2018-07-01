package ru.serdar1980.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Quiz {
    private List<Question> questions = new ArrayList<>();

    public List<Question> getQuestions() {
        return new ArrayList<Question>(questions);
    }

    public void setQuestions(List<Question> questions) {
        this.questions = new ArrayList<Question>(questions);
    }
}
