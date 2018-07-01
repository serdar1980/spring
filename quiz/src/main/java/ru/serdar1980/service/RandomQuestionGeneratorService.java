package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.serdar1980.domain.Question;
import ru.serdar1980.domain.Quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RandomQuestionGeneratorService {

    private Quiz quiz;

    @Value("${max.questions}")
    private Integer maxQuestion;

    @Autowired
    public RandomQuestionGeneratorService(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Question> getRandomQuiz() {
        List<Question> questions = new ArrayList<>();
        if (maxQuestion != null && maxQuestion > 0) {
            int count = maxQuestion;
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
