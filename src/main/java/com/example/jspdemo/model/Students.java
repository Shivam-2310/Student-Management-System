package com.example.jspdemo.model;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name="STUDENTS")
public class Students {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "random_date_time")
    private LocalDateTime randomDateTime;

    @Column
    private String name;

    @Column
    private String college;

    @Column
    private int year;

    @Column
    private boolean checkboxOption;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public boolean isCheckboxOption() {
        return checkboxOption;
    }

    public void setCheckboxOption(boolean checkboxOption) {
        this.checkboxOption = checkboxOption;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}



