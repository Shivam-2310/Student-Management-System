package com.example.jspdemo.service;
import com.example.jspdemo.model.Students;
import com.example.jspdemo.repo.IStudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class StudentsService {

    @Autowired
    IStudentsRepository studentsRepo;

    public List<Students> getAllStudents() {
        List<Students> studentsList = new ArrayList<>();
        studentsRepo.findAll().forEach(students -> studentsList.add(students));
        return studentsList;
    }

    public Students getStudentsById(Long id) {
        return studentsRepo.findById(id).get();
    }

    public boolean saveOrUpdateStudents(Students students) {
        Students updatedStudents = studentsRepo.save(students);
        return studentsRepo.existsById(updatedStudents.getId());
    }


    public boolean deleteStudents(Long id) {
        studentsRepo.deleteById(id);

        if (studentsRepo.findById(id) != null) {
            return true;
        }

        return false;
    }


    public void generateRandomStudents(int count) {

        List<String> collegeOptions = List.of("DU", "GGSIPU", "AKTU", "VIT", "SRM");

        for (int i = 0; i<count; i++) {
            Students students = new Students();
            students.setName(generateRandomName());
            students.setYear(generateRandomYear());
            students.setCollege(collegeOptions.get(generateRandomNumber(0, collegeOptions.size() - 1)));
            students.setCheckboxOption(generateRandomBoolean());
            studentsRepo.save(students);

        }

    }

    private static final String[] NAMES = {
            "Aditya", "Bhavika", "Bhumika", "Abhedaya", "Jasraj", "Harshit", "Shivam", "Sourabh", "Ramneet", "Tanmay", "Sahaj", "Aksh"
    };

    private final Random random = new Random();

    public String generateRandomName () {
        int randomIndex = random.nextInt(NAMES.length);
        return NAMES[randomIndex];
    }

    public int generateRandomYear () {
        return random.nextInt(4) + 1; // Generates 1 to 4
    }

    public int generateRandomNumber ( int min, int max){
        return random.nextInt(max - min + 1) + min;
    }

    public boolean generateRandomBoolean () {
        return random.nextBoolean();
    }


}

