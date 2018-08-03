package ru.serdar1980;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.serdar1980.dao.BaseDao;
import ru.serdar1980.service.AuthorDBService;

@SpringBootApplication
public class Main {

    public static void main(String... args) {
        SpringApplication.run(Main.class);
        AnnotationConfigApplicationContext cnt = new AnnotationConfigApplicationContext(Main.class);
        AuthorDBService service = (AuthorDBService) cnt.getBean("authorDBService");
        BaseDao dao = service.getDao();
    }

}