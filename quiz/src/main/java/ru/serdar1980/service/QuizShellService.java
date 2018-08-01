package ru.serdar1980.service;

public interface QuizShellService {
    String getQuestion();

    String setAnswer(String answer);

    String create(String name);
}
