package ru.serdar1980.domain;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private List<Question> questions;
    private Integer result = 0;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return new ArrayList<Question>(questions);
    }

    public void setQuestions(List<Question> questions) {

        this.questions = new ArrayList<Question>(questions);
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public void increment() {
        this.result++;
    }
}
