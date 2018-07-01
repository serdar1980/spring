package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.serdar1980.domain.Answer;
import ru.serdar1980.domain.Question;
import ru.serdar1980.domain.Quiz;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReaderService {

    private static final Integer MAX_CVS_ABSWER_DEF = 2;
    private static final String SPLIT_BY = ";";
    @Value("${quiz.csv.splitby}")
    private String cvsSplitBy;
    @Value("${quiz.csv.max.answer}")
    private Integer maxCvsAnswer;
    private Quiz quiz;

    private String csvFilename = "quiz.csv";

    @Autowired
    public CsvReaderService(Quiz quiz) {
        this.quiz = quiz;
    }

    @PostConstruct
    public void readFile() {
        if (maxCvsAnswer == null) {
            maxCvsAnswer = MAX_CVS_ABSWER_DEF;
        }
        if (cvsSplitBy == null) {
            cvsSplitBy = SPLIT_BY;
        }
        List<Question> questions = new ArrayList<>();
        if (csvFilename != null) {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(csvFilename).getFile());
            try (BufferedReader br =
                         new BufferedReader(new FileReader(file))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    Question question = null;
                    // use comma as separator
                    String[] metadata = line.split(cvsSplitBy);
                    maxCvsAnswer = metadata.length;
                    if (metadata.length == maxCvsAnswer) {
                        List<Answer> answers = new ArrayList<>();
                        question = new Question(metadata[0]);
                        boolean flag = Boolean.TRUE;
                        for (int j = 1; j < maxCvsAnswer; j++) {
                            Answer answer = new Answer(metadata[j], flag);
                            answers.add(answer);
                            flag = Boolean.FALSE;
                        }
                        question.setAnswers(answers);
                    }
                    if (question != null) {
                        questions.add(question);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        quiz.setQuestions(questions);
    }
}
