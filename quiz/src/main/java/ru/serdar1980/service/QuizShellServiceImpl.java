package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.serdar1980.domain.Answer;
import ru.serdar1980.domain.Question;
import ru.serdar1980.domain.Student;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Service
public class QuizShellServiceImpl implements QuizShellService {

    private GeneratorService generatorService;
    private List<Answer> answers = null;
    private Student student;

    @Autowired
    public QuizShellServiceImpl(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    private static void checkAnswer(List<Answer> answers, Student student) {
        Scanner scanner = new Scanner(System.in);
        Integer res = -1;
        try {
            res = scanner.nextInt();
            if (res > answers.size() || res < 0) {
                System.out.println(MessageLocateService.getMessage("lable.anwer"));
                scanner = null;
                checkAnswer(answers, student);
                return;
            }
        } catch (InputMismatchException ex) {
            scanner = null;
            System.out.println(MessageLocateService.getMessage("lable.anwer"));
            checkAnswer(answers, student);
            return;
        }

        Answer answer = answers.get(res - 1);
        if (answer.isTrue()) {
            student.increment();
        }
    }

    @Override
    public String getQuestion() {
        Scanner scanner = new Scanner(System.in);
        int answerCnt = 1;
        List<Answer> answers = null;
        List<Question> questions = student.getQuestions();
        for (Question q : questions) {
            System.out.println(q.getQuestion());
            answers = q.getAnswers();
            Collections.shuffle(answers);
            int number = 1;
            for (Answer a : answers) {
                StringBuilder sb = new StringBuilder();
                sb.append(number).append(": ").append(a.getAnswer()).append("\r\n");
                number++;
                System.out.println(sb.toString());
            }
            if (answerCnt <= questions.size()) {
                checkAnswer(answers, student);
            }
            answerCnt++;
        }
        return MessageLocateService.getMessage("lable.result", new Object[]{student.getResult()});
    }

    @Override
    public String setAnswer(String answer) {
        return answer;
    }

    @Override
    public String create(String name) {
        student = new Student(name);
        student.setQuestions(generatorService.getRandomQuiz());
        return getQuestion();
    }
}