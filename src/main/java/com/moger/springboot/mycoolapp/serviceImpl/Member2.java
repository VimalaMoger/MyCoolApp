package com.moger.springboot.mycoolapp.serviceImpl;

import com.moger.springboot.mycoolapp.service.Meetup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
//@Primary
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Member2 implements Meetup {

    //Define our init method
    @PostConstruct
    public void startUpToDoTask(){
        System.out.println("To Do tasks: " + getClass().getSimpleName());
    }

    //Define our destroy method
    @PreDestroy
    public void cleanUpToDoTask(){
        System.out.println("Cleaning up tasks: " + getClass().getSimpleName());
    }

    @Override
    public Date join() {
        return new Date();
    }

    @Override
    public String name() {
        return "Rocky";
    }

    @Override
    public String toString(){
        return "[ Date joined- "+ join() + " and Name is: "+ name() + " ]";
    }
}
