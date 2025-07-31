package com.moger.springboot.util;

import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class TimeWhenMessageCreated {
    public Date returnDate(){
        return new Date();
    }
    public String sendMessage(){
        return "This is a SMS message";
    }

    @Override
    public String toString(){
        return "[ Date created- "+ returnDate() + " and Message is: "+ sendMessage() + " ]";
    }
}
