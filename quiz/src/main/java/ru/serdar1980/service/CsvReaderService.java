package ru.serdar1980.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.serdar1980.domain.Answer;
import ru.serdar1980.domain.Question;
import ru.serdar1980.domain.Quiz;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReaderService {

    private static final Integer MAX_CVS_ABSWER_DEF = 2;
    @Value("${quiz.csv.splitby}")
    private String cvsSplitBy = ";";
    @Value("${quiz.csv.max.answer}")
    private Integer maxCvsAnswer;
    private Quiz quiz;

    //@Value("${quiz.csv.filename}")
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
                    if (metadata.length == maxCvsAnswer) {
                        List<Answer> answers = new ArrayList<>();
                        question = new Question(metadata[0]);
                        boolean flag = Boolean.TRUE;
                        for (int j = 1; j < maxCvsAnswer; j++) {
                            String strAnswer = "";
                            try {
                                strAnswer = metadata[j];
                            } catch (IndexOutOfBoundsException ex) {
                                break;
                            }
                            Answer answer = new Answer(strAnswer, flag);
                            answers.add(answer);
                            flag = Boolean.FALSE;
                        }
                        question.setAnswers(answers);
                    }
                    if (question != null) {
                        questions.add(question);
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        quiz.setQuestions(questions);
    }
}
