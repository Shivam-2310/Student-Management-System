package com.example.jspdemo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.jspdemo.model.Students;
import com.example.jspdemo.service.StudentsService;

@Component
public class DataGenerator implements CommandLineRunner {

    private final StudentsService studentsService;

    public DataGenerator(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @Override
    public void run(String... args) throws Exception {
        studentsService.generateRandomStudents(100);

    }


}
