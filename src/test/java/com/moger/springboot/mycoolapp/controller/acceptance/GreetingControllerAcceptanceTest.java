package com.moger.springboot.mycoolapp.controller.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moger.springboot.mycoolapp.config.MeetupConfig;
import com.moger.springboot.mycoolapp.entity.Greeting;
import com.moger.springboot.mycoolapp.service.Meetup;
import com.moger.springboot.util.TimeWhenMessageCreated;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.Calendar;
import java.util.Date;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest   //starts Spring application context making all beans available for injection
@AutoConfigureMockMvc
public class GreetingControllerAcceptanceTest {

    //send request
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /** Injecting dependent classes */
    @Autowired
    private TimeWhenMessageCreated timeWhenMessageCreated;

    @Autowired
    @Qualifier("member2")
    private Meetup newMember;

    @Autowired
    private MeetupConfig meetup_Time;

    @Test
    public void shouldGetHelloMessage() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

    @Test
    public void shouldGetCustomHelloMessage() throws Exception {
        mockMvc.perform(get("/hello?name=Dan"))
                .andDo(print())
                .andExpect(content().string("Hello Dan"));
    }

    @Test
    void shouldGetCustomGreetingMessage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/greeting?name=Dan"))
                .andDo(print())
                .andReturn();

        Greeting greeting = new Greeting(492, "Hello Dan!");
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(greeting));
    }

    @Test
    public void shouldGetCustomUserName() throws Exception {

        Greeting greeting = new Greeting(246, "This is Ria Gosh!");

        mockMvc.perform(get("/user")
                        .param("name", "${my.app.user.name}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(objectMapper.writeValueAsString(greeting))));
    }

    @Test
    public void shouldGetMessageUpdatedTime() throws Exception {

        mockMvc.perform(get("/createdOn"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(timeWhenMessageCreated.toString())));
    }

    @Test
    public void shouldGetMemberInfo() throws Exception {

        mockMvc.perform(get("/memberInfo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(newMember.toString()));
    }

    @Test
    public void shouldGetNewMeetupTime() throws Exception{

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        mockMvc.perform(get("/newMeetup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Starts in " + hour + " hours promptly")));
    }

}
