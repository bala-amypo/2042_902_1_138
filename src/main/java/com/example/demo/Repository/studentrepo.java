package com.example.demo.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.repository;
@Repository
public interface studentrepo extends Jparepository<student,Integer>{

}