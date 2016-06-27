package tobias.standup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tobias.standup.api.hipchat.HipchatRequest;
import tobias.standup.api.hipchat.HipchatResponse;
import tobias.standup.api.hipchat.Item;
import tobias.standup.api.hipchat.Message;
import tobias.standup.businessdelegate.IHipChatBusinessDelegate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This test ensures that the JSON mapping is functioning correctly, along with generating the REST documentation.
 * Created by tobias on 25/06/16.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ControllerTestConfiguration.class)
@WebAppConfiguration
public class HipChatStandupControllerTest {

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private IHipChatBusinessDelegate mockBusinessDelegate;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation).uris().withHost("example.server").withPort(80))
                .build();
    }

    @Test
    public void standup_RequestMapping_RequestIsMappedCorrectly() throws Exception {

        HipchatResponse mockResponse = new HipchatResponse();
        mockResponse.setMessage("Example Response");
        mockResponse.setMessageFormat("ExampleFormat");
        Mockito.when(this.mockBusinessDelegate.processWebhook(Mockito.any())).thenReturn(mockResponse);

        HipchatRequest request = new HipchatRequest();
        Item item = new Item();
        request.setItem(item);
        Message message = new Message();
        message.setMessage("Example Request");
        item.setMessage(message);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        MvcResult mockMvcResult = this.mockMvc.perform(post("/hipchat-standup").content(requestJson)
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("index")).andReturn();

        String stringResult = mockMvcResult.getResponse().getContentAsString();

        HipchatResponse response = mapper.readValue(stringResult, HipchatResponse.class);
        Assert.assertThat(response.getMessage(), is(equalTo("Example Response")));
        Assert.assertThat(response.getMessageFormat(), is(equalTo("ExampleFormat")));

        ArgumentCaptor<HipchatRequest> argumentCaptor = ArgumentCaptor.forClass(HipchatRequest.class);

        Mockito.verify(this.mockBusinessDelegate).processWebhook(argumentCaptor.capture());

        Assert.assertThat(argumentCaptor.getValue().getItem().getMessage().getMessage(), is(equalTo("Example Request")));

    }

}
