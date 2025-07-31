package com.moger.springboot.mycoolapp.serviceImpl;

import com.moger.springboot.mycoolapp.service.Meetup;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
//@Lazy
public class Member1 implements Meetup {
    @Override
    public Date join() {
        return new Date();
    }

    @Override
    public String name() {
        return "Sally";
    }
}
