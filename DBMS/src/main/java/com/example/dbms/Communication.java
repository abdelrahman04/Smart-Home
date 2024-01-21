package com.example.dbms;

import lombok.Data;

@Data
public class Communication {

    private int message_id;
    private int sender_id;
    private int receiver_id;
    private String content;
    private String time_sent;
    private String time_received;
    private String time_read;
    private String title;

}
