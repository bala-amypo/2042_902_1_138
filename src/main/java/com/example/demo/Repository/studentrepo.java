package com.example.demo.repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.repository;
@repository
public interface studentrepo extends Jparepository<student,Integer>{

}