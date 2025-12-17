package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.student;

@Repository
public interface studentrepo extends JpaRepository<student,Integer>{

}