package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.serdar1980.condig.MaxValue;
import ru.serdar1980.domain.Question;
import ru.serdar1980.domain.Quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RandomQuestionGeneratorService implements GeneratorService {

    private Quiz quiz;
    @Autowired
    private MaxValue maxValue;

    @Autowired
    public RandomQuestionGeneratorService(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Question> getRandomQuiz() {
        List<Question> questions = new ArrayList<>();
        if (maxValue.getQuestions() != null && maxValue.getQuestions() > 0) {
            int count = maxValue.getQuestions();
            List<Question> list = quiz.getQuestions();
            while (count != 0) {
                Random rand = new Random();
                int numOfQuest = rand.nextInt(list.size());
                Question question = list.get(numOfQuest);
                list.remove(question);
                questions.add(question);
                count--;
            }
        }
        return questions;
    }
}
