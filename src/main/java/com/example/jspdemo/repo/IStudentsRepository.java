package com.example.jspdemo.repo;

import com.example.jspdemo.model.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentsRepository extends JpaRepository<Students, Long> {

}
