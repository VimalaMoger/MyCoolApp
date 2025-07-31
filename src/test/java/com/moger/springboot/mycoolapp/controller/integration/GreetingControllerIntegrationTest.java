package com.moger.springboot.mycoolapp.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moger.springboot.mycoolapp.config.MeetupConfig;
import com.moger.springboot.mycoolapp.controller.GreetingController;
import com.moger.springboot.mycoolapp.entity.Greeting;
import com.moger.springboot.util.ScheduleNewMeetup;
import com.moger.springboot.mycoolapp.service.Meetup;
import com.moger.springboot.util.TimeWhenMessageCreated;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.Calendar;
import java.util.Date;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration Test between GreetingController and HTTP layer
 */

//@ExtendWith(SpringExtension.class) optional SpringBoot 2.1 Onwards
@WebMvcTest(controllers = GreetingController.class)    //Class under test
@TestPropertySource(properties = {
        "my.app.user.name=Ria Gosh"
})
public class GreetingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Greeting greeting;

    @MockBean
    @Qualifier("member2")
    private Meetup newMember;

    @MockBean
    private MeetupConfig meetup_Time;

    @MockBean
    private TimeWhenMessageCreated timeWhenMessageCreated;

    /**
     * Verifying HTTP response to /hello request - HTTP request parameter
     * @throws Exception
     */
    @Test
    public void shouldGetHelloMessage() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

    //Verifying HTTP response to /hello request
    @Test
    public void shouldGetCustomHelloMessage() throws Exception{
        mockMvc.perform(get("/hello?name=Dan"))
                .andDo(print())
                .andExpect(content().string("Hello Dan"));
    }

    /**
     * Verifying Output Serialization -  actual Greeting object from HTTP response in JSON form against
     * the expected JSON String from Greeting object wrapped in ObjectMapper
     * @throws Exception
     */
    @Test
    void shouldGetCustomGreetingMessage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/greeting?name=Dan"))
                .andDo(print())
                .andReturn();

        greeting = new Greeting(492, "Hello Dan!");
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(greeting));
    }

    /**
     * Verifying HTTP response to /user request - custom property user.name
     * @throws Exception
     */
    @Test
    public void shouldGetCustomUserName() throws Exception {

        greeting = new Greeting(246, "This is Ria Gosh!");

        mockMvc.perform(get("/user")
                .param("name", "${my.app.user.name}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(objectMapper.writeValueAsString(greeting))));
    }

    /**
     * Verifying HTTP response to /createdOn request
     * @throws Exception
     */
    @Test
    public void shouldGetMessageUpdatedTime() throws Exception{

        mockMvc.perform(get("/createdOn"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().string(timeWhenMessageCreated.toString()));
    }

    /**
     * Verifying HTTP response to /memberInfo request
     * @throws Exception
     */
    @Test
    public void shouldGetMemberInfo() throws Exception{
        mockMvc.perform(get("/memberInfo"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().string(newMember.toString()));
    }

    /**
     * Verifying HTTP response to /newMeetup request
     * @throws Exception
     */
    @Test
    public void shouldGetNewMeetupTime() throws Exception{

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        when(meetup_Time.newMeetup()).thenReturn(new ScheduleNewMeetup());

        mockMvc.perform(get("/newMeetup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Starts in " + hour + " hours promptly")));
        verify(meetup_Time).newMeetup();
    }
}
