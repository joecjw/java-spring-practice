package com.joecjw.SpringBootProjectPractice.utils;

import lombok.extern.java.Log;
import org.slf4j.Logger;

import java.time.LocalDateTime;

public class TimeUtil {

    public static LocalDateTime parseDDL(String time){
        try{
            String[] index = time.split(":");
            if(index.length != 4) {
                return null;
            }
            LocalDateTime localDateTime = LocalDateTime
                    .now()
                    .plusDays(Long.parseLong(index[0].trim()))
                    .plusHours(Long.parseLong(index[1].trim()))
                    .plusMinutes(Long.parseLong(index[2].trim()))
                    .plusSeconds(Long.parseLong(index[3].trim()));
            return localDateTime;
        }catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime parseDDLFromCreationTime(String time, LocalDateTime creationTime){

        try{
            String[] index = time.split(":");
            if(index.length != 4) {
                return null;
            }
            LocalDateTime localDateTime = creationTime
                                            .plusDays(Long.parseLong(index[0].trim()))
                                            .plusHours(Long.parseLong(index[1].trim()))
                                            .plusMinutes(Long.parseLong(index[2].trim()))
                                            .plusSeconds(Long.parseLong(index[3].trim()));
            return localDateTime;
        }catch (Exception e) {
            return null;
        }
    }
}
