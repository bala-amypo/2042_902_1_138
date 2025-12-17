package com.example.demo.repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository;
import org.springframework.stereotype.repository;
@Repository
public interface studentrepo extends Jparepository<student,Integer>{

}