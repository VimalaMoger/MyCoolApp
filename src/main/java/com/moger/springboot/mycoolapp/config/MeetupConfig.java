package com.moger.springboot.mycoolapp.config;

import com.moger.springboot.util.ScheduleNewMeetup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeetupConfig {

    @Bean
    public ScheduleNewMeetup newMeetup(){
        return new ScheduleNewMeetup();
    }
}
