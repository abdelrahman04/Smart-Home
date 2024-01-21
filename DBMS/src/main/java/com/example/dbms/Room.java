package com.example.dbms;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Room {
    @Id
    private int room_id ;
    private String type;
    private String floor;
    private String status;
}
