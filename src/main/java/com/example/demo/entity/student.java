package com.example.demo.entity;
import jakarta.persistence.*;
@Entity
public class student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){

        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public student(){

    }
public student(Integer id,String name,String email){
    this.id=id;
    this.name=name;
    this.email=email;
}
}