package com.example.dbms;


import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Users {
    private int id ;
    private String f_Name;
    private String l_Name;
    private String password;
    private String email;
    private String preference;
    private Integer room;
    private String type;
    private String birthdate ;
    private int age;
    private int NumberofGuests;
}
