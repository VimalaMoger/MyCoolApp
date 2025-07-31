package com.moger.springboot.mycoolapp.controller;

import com.moger.springboot.mycoolapp.config.MeetupConfig;
import com.moger.springboot.mycoolapp.entity.Greeting;
import com.moger.springboot.mycoolapp.service.Meetup;
import com.moger.springboot.util.TimeWhenMessageCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    public static final String text = "Hello %s!";
    private final AtomicLong atomicLong_Initial_Value = new AtomicLong(123);

    //Dependent objects
    private final TimeWhenMessageCreated timeWhenMessageCreated;
    private final Meetup newMember;
    private final MeetupConfig meetup_Time;

    //Inject properties for username
    @Value("${my.app.user.name}")
    private String userName;

    @Autowired
    public GreetingController(@Qualifier("member2") Meetup aMember, MeetupConfig meetupTime, TimeWhenMessageCreated timeWhenMessageCreated){
        this.timeWhenMessageCreated = timeWhenMessageCreated;
        this.newMember = aMember;
        this.meetup_Time = meetupTime;
    }

    //HTTP calls

    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "World") String name){
        return "Hello " + name;
    }

    @GetMapping("/greeting")
    public Greeting getMessage(@RequestParam(defaultValue = "World") String name){
        return new Greeting(atomicLong_Initial_Value.addAndGet(atomicLong_Initial_Value.get()), String.format(text, name));
    }

    @GetMapping("/user")
    public Greeting getUserName(){
        return new Greeting(atomicLong_Initial_Value.addAndGet(atomicLong_Initial_Value.get()), String.format("This is %s!", userName));
    }

    @GetMapping("/createdOn")
    public String getTimeWhenMessageUpdated(){
        return timeWhenMessageCreated.toString();
    }

    @GetMapping("/memberInfo")
    public String getMemberInfo(){
        return newMember.toString();
    }

    @GetMapping("/newMeetup")
    public String newMeetupTime(){
        return meetup_Time.newMeetup().newMeetUpTime();
    }
}
