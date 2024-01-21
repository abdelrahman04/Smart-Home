package com.example.dbms;

import lombok.Data;

@Data
public class Task {
    private int Task_id;
    private String name;
    private String creation_date;
    private String due_date;
    private String category;
    private int creator;
    private String status;
    private String reminder_date;
    private int priority;

}
