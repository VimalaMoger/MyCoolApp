package com.moger.springboot.mycoolapp.controller.unittest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moger.springboot.mycoolapp.config.MeetupConfig;
import com.moger.springboot.mycoolapp.controller.GreetingController;
import com.moger.springboot.mycoolapp.entity.Greeting;
import com.moger.springboot.mycoolapp.service.Meetup;
import com.moger.springboot.util.ScheduleNewMeetup;
import com.moger.springboot.util.TimeWhenMessageCreated;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  //JUnit5
//@RunWith(MockitoJUnitRunner.class)  //for JUnit4
class GreetingControllerUnitTest {

    //Dependent mocked objects
    @Mock
    private Meetup newMember;

    @Mock
    private MeetupConfig meetup_time;

    @Mock
    private ScheduleNewMeetup scheduleNewMeetup;

    @Mock
    private TimeWhenMessageCreated timeWhenMessageCreated;

    @Mock
    private Greeting greeting;

    //Class under test
    @InjectMocks
    private GreetingController controller;

    @Test
    void shouldGetHelloMessage() {
        assertEquals("Hello World", controller.hello("World"));
    }

    @Test
    void shouldGetGreetingMessage() throws JsonProcessingException {
        greeting = new Greeting(246, "Hello Ria Gosh!");
        assertEquals(greeting, controller.getMessage("Ria Gosh"));
    }

    @Test
    void getTimeWhenMessageUpdated() {
        Mockito.when(timeWhenMessageCreated.toString()).thenReturn("\"[ Date created- \"+ returnDate() + \" and Message is: \"+ sendMessage() + \" ]\"");
        assertEquals(timeWhenMessageCreated.toString(), controller.getTimeWhenMessageUpdated());
    }

    @Test
    void getMemberInfo() {
        when(newMember.toString()).thenReturn("\"[ Date joined- \"+ new Date() + \" and Name is: \"+ name() + \" ]\"");
        assertEquals(newMember.toString(), controller.getMemberInfo());
    }

    @Test
    void getNewMeetupTime() {
        Mockito.when(meetup_time.newMeetup()).thenReturn(scheduleNewMeetup);
        when(scheduleNewMeetup.newMeetUpTime()).thenReturn("\"[ Date joined- \"+ new Date() + \" and Name is: \"+ name() + \" ]\"");
        assertEquals(meetup_time.newMeetup().newMeetUpTime(), controller.newMeetupTime());
    }
}
