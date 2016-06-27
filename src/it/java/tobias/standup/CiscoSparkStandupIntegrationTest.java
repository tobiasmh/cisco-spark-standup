package tobias.standup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tobias.standup.api.ciscospark.dto.CiscoSparkWebhookRequest;
import tobias.standup.api.ciscospark.dto.Data;
import tobias.standup.api.ciscospark.sdk.Message;
import tobias.standup.api.ciscospark.sdk.Room;
import tobias.standup.api.ciscospark.sdk.Spark;
import tobias.standup.api.ciscospark.sdk.Webhook;
import tobias.standup.businessdelegate.ICiscoSparkBusinessDelegate;
import tobias.standup.service.ICiscoSparkService;

import java.util.Iterator;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * WARNING: This test is destructive, so be sure to only supply test account credentials.
 * This integration test performs the following steps:
 * Before
 * - Clean the Cisco Spark Environment by deleting all existing rooms (and webhooks)
 *
 * standupWebhook_WebhookAddedToNewRoomAndStandupFunctions_StandupReportIsProduced Test
 * - Create a new room for this test
 * - Trigger the update of the webhook subscriptions
 * - Check using the Spark API that the webhook has been succesfully created
 * - Simulate a webhook request for adding a new standup status
 * - Check room to ensure message is correctly populated
 * - Simulate a webhook request for adding a new standup status
 * - Use Spark API to ensure the messages have been correctly posted
 *
 * After
 * - Clean the Cisco Spark Environment by deleting all existing rooms (and webhooks)
 *
 * Created by tobias on 25/06/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StandupApplication.class)
@WebAppConfiguration
public class CiscoSparkStandupIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    protected Spark sparkApi;

    @Autowired
    private ICiscoSparkBusinessDelegate businessDelegate;

    @Autowired
    private ICiscoSparkService sparkService;

    @Value("${ciscoSparkApiKeyEmailAddress}")
    private String ciscoSparkApiKeyEmailAddress;

    @Before
    public void before() {
        cleanup();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @After
    public void after() {
        cleanup();
    }

    private void cleanup() {
        this.sparkApi.rooms().iterate().forEachRemaining(room -> {
            this.sparkApi.rooms().path("/"+room.getId()).delete();
        });
    }

    private Message getMessage(String roomId, String messageContent) {
        Iterator<Message> messageIterator = this.sparkApi.messages().queryParam("roomId", roomId).iterate();
        while (messageIterator.hasNext()) {
            Message message = messageIterator.next();
            if (message.getText().equals(messageContent)) {
                return message;
            }
        }
        return null;
    }

    private String convertObjectToJson(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);

    }

    @Test
    public void standupWebhook_WebhookAddedToNewRoomAndStandupFunctions_StandupReportIsProduced() throws Exception {

        // Create new room
        String roomName = String.format("Example Standup Room %s", UUID.randomUUID().toString());
        Room newRoom = new Room();
        newRoom.setTitle(roomName);
        this.sparkApi.rooms().post(newRoom);
        newRoom = this.sparkApi.rooms().iterate().next();

        // Create the webhook subscription, and ensure that it is correctly created
        this.sparkService.updateWebhookSubscriptions();
        Webhook webhook = this.sparkApi.webhooks().iterate().next();
        Assert.assertThat(webhook.getName(), is(equalTo("Standup Bot Webhook")));
        Assert.assertThat(webhook.getFilter(), is(equalTo(String.format("roomId=%s", newRoom.getId()))));


        // Submit standup status report, and then inject a mock webhook notification
        Message standupStatusMessage = new Message();
        standupStatusMessage.setText("/standup example report");
        standupStatusMessage.setRoomId(newRoom.getId());
        this.sparkApi.messages().post(standupStatusMessage);
        String standupStatusMessageId = getMessage(newRoom.getId(), standupStatusMessage.getText()).getId();

        CiscoSparkWebhookRequest standupStatus = new CiscoSparkWebhookRequest();
        Data standupStatusData = new Data();
        standupStatusData.setPersonId(this.sparkApi.people().queryParam("email", this.ciscoSparkApiKeyEmailAddress).iterate().next().getId());
        standupStatusData.setRoomId(newRoom.getId());
        standupStatusData.setId(standupStatusMessageId);
        standupStatus.setData(standupStatusData);

        this.mockMvc.perform(post("/cisco-spark-standup").content(convertObjectToJson(standupStatus))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        // Submit standup report request, and then inject a mock webhook notification
        Message standupReportMessage = new Message();
        standupReportMessage.setText("/standup");
        standupReportMessage.setRoomId(newRoom.getId());
        this.sparkApi.messages().post(standupReportMessage);
        String standupReportMessageId = getMessage(newRoom.getId(), standupReportMessage.getText()).getId();

        CiscoSparkWebhookRequest standupReportWebhook = new CiscoSparkWebhookRequest();
        Data standupReportWebhookData = new Data();
        standupReportWebhookData.setPersonId(this.sparkApi.people().queryParam("email", this.ciscoSparkApiKeyEmailAddress).iterate().next().getId());
        standupReportWebhookData.setRoomId(newRoom.getId());
        standupReportWebhookData.setId(standupReportMessageId);
        standupReportWebhook.setData(standupReportWebhookData);

        this.mockMvc.perform(post("/cisco-spark-standup").content(convertObjectToJson(standupReportWebhook))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        // Retrieve the generated standup report
        Message standupReportReplyMessage = getMessage(newRoom.getId(), "Standup Bot: example report\n");
        Assert.assertNotNull(standupReportReplyMessage);

    }
}
