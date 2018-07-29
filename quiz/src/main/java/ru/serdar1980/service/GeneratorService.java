package ru.serdar1980.service;

import ru.serdar1980.domain.Question;

import java.util.List;

public interface GeneratorService {

    List<Question> getRandomQuiz();
}
