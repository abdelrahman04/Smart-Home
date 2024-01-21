package com.example.dbms;

import lombok.Data;

@Data
public class Calendar {
    private int event_id;
    private int user_assigned_to;
    private String name;
    private String description;
    private String location;
    private String reminder_date;
}
