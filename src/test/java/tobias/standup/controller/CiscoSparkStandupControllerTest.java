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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tobias.standup.api.ciscospark.dto.CiscoSparkWebhookRequest;
import tobias.standup.api.ciscospark.dto.Data;
import tobias.standup.businessdelegate.ICiscoSparkBusinessDelegate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This test ensures that the JSON mapping is functioning correctly, along with generating the REST documentation.
 * Created by tobias on 26/06/16.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ControllerTestConfiguration.class)
@WebAppConfiguration
public class CiscoSparkStandupControllerTest {

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ICiscoSparkBusinessDelegate mockBusinessDelegate;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation).uris().withHost("example.server").withPort(80))
                .build();
    }

    @Test
    public void webhook_RequestMapping_RequestIsMappedCorrectly() throws Exception {

        CiscoSparkWebhookRequest mockRequest = new CiscoSparkWebhookRequest();
        Data mockRequestData = new Data();
        mockRequestData.setPersonId("PersonId");
        mockRequestData.setRoomId("RoomId");
        mockRequestData.setId("MessageId");
        mockRequest.setData(mockRequestData);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(mockRequest);

        this.mockMvc.perform(post("/cisco-spark-standup").content(requestJson)
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(document("index"));


        ArgumentCaptor<CiscoSparkWebhookRequest> argumentCaptor = ArgumentCaptor.forClass(CiscoSparkWebhookRequest.class);

        Mockito.verify(this.mockBusinessDelegate).processWebhook(argumentCaptor.capture());

        Data capturedData = argumentCaptor.getValue().getData();
        Assert.assertThat(capturedData.getRoomId(), is(equalTo("RoomId")));
        Assert.assertThat(capturedData.getId(), is(equalTo("MessageId")));
        Assert.assertThat(capturedData.getPersonId(), is(equalTo("PersonId")));
    }

    @Test
    public void updateStandupWebhookSubscriptions_BusinessDelegateCalled_BusinessDelegateIsCalled() throws Exception {
        this.mockMvc.perform(get("/cisco-spark-standup-webhook-refresh"))
                .andExpect(status().is2xxSuccessful())
                .andDo(document("index"));
        Mockito.verify(this.mockBusinessDelegate, Mockito.times(1)).updateStandupWebhookSubscriptions();
    }


}
