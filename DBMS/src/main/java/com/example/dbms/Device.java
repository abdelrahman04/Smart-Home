package com.example.dbms;

import lombok.Data;

@Data
public class Device {
    private int device_id;
    private int room;
    private String type;
    private String status;
    private int battery_status;
}
