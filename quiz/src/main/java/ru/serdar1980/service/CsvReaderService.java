package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.serdar1980.condig.Csv;
import ru.serdar1980.condig.MaxValue;
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

    private Csv csv;
    private MaxValue maxValue;
    private static final Integer MAX_CVS_ABSWER_DEF = 2;
    private static final String SPLIT_BY = ";";
    private Quiz quiz;
    private Integer maxCvsAnswer;
    private String cvsSplitBy;
    private String csvFilename = "quiz.csv";

    @Autowired
    public CsvReaderService(Quiz quiz) {
        this.quiz = quiz;
    }

    @Autowired
    public void setCsv(Csv csv) {
        this.csv = csv;
    }

    @Autowired
    public void setMaxValue(MaxValue maxValue) {
        this.maxValue = maxValue;
    }

    @PostConstruct
    public void readFile() {
        if (maxValue.getAnswer() == null) {
            maxCvsAnswer = MAX_CVS_ABSWER_DEF;
        } else {
            maxCvsAnswer = maxValue.getAnswer();
        }
        if (csv.getCvsSplitBy() == null) {
            cvsSplitBy = SPLIT_BY;
        } else {
            cvsSplitBy = csv.getCvsSplitBy();
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
