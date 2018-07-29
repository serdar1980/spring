package ru.serdar1980.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.serdar1980.service.QuizShellService;

@ShellComponent
public class QuizCommand {
    private QuizShellService service;

    @Autowired
    public QuizCommand(QuizShellService service) {
        this.service = service;
    }

    @ShellMethod("Получить вопрос")
    public String getQuestion() {
        return service.getQuestion();
    }

    @ShellMethod("Получить ответ")
    public String setAnswer(
            @ShellOption String answer
    ) {
        return service.setAnswer(answer);
    }

    @ShellMethod("Создать студента")
    public String create(
            @ShellOption String name
    ) {

        return service.create(name);
    }

}
