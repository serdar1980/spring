package ru.serdar1980;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.serdar1980.domain.Answer;
import ru.serdar1980.domain.Question;
import ru.serdar1980.domain.Student;
import ru.serdar1980.service.CsvReaderService;
import ru.serdar1980.service.MessageLocateService;
import ru.serdar1980.service.RandomQuestionGeneratorService;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Configuration
@ComponentScan
public class Main {

    public static void main(String... args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        CsvReaderService csvReaderService = context.getBean(CsvReaderService.class);

        System.out.println(MessageLocateService.getMessage("label.registration"));
        Scanner scanner = new Scanner(System.in);

        String studentName = scanner.next();

        Student student = new Student(studentName);

        RandomQuestionGeneratorService genRandomQuiz =
                context.getBean(RandomQuestionGeneratorService.class);

        student.setQuestions(genRandomQuiz.getRandomQuiz());

        System.out.println(MessageLocateService.getMessage("lable.anwer"));

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
            if (answerCnt < questions.size()) {
                checkAnswer(answers, student);
            }
            answerCnt++;
        }
        Integer studentAnswer = scanner.nextInt();


        System.out.println(MessageLocateService.getMessage("lable.result", new Object[]{student.getResult()}));


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
}
