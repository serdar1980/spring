package ru.serdar1980.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class Student {
    String name;
    List<Question> questions;
    Integer result = 0;

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
