package tobias.standup.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import tobias.standup.api.ciscospark.ICiscoSparkApiWrapper;
import tobias.standup.api.ciscospark.sdk.Message;
import tobias.standup.api.ciscospark.sdk.Person;
import tobias.standup.api.ciscospark.sdk.Room;
import tobias.standup.api.ciscospark.sdk.Webhook;
import tobias.standup.entity.StandupStatus;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by tobias on 25/06/16.
 */
public class CiscoSparkServiceTest {

    private ICiscoSparkApiWrapper mockSparkApi = Mockito.mock(ICiscoSparkApiWrapper.class);

    private IStandupService mockStandupService = Mockito.mock(IStandupService.class);

    private CiscoSparkService service;

    @Before
    public void before() {
        this.service = new CiscoSparkService(this.mockStandupService, this.mockSparkApi);
        this.service.setWebhookTargetUrl("http://example.org/webhook");
    }

    @Test
    public void processWebhook_MessageMappingForNewStandupStatus_StandupServiceIsCalledWithCorrectParameters() {

        Message message = new Message();
        message.setText("/standup Its a big message");
        Mockito.when(this.mockSparkApi.getMessage(Mockito.anyString(), Mockito.anyString())).thenReturn(message);

        Person person = new Person();
        person.setId("PersonId");
        person.setDisplayName("Example Display Name");
        person.setEmails(new String[]{"test@example.org"});
        Mockito.when(this.mockSparkApi.getPerson(Mockito.anyString())).thenReturn(person);

        this.service.processWebhook("RoomId", "PersonId", "MessageId");

        ArgumentCaptor<StandupStatus> argumentCaptor = ArgumentCaptor.forClass(StandupStatus.class);
        Mockito.verify(this.mockStandupService).saveStandupStatus(argumentCaptor.capture());

        StandupStatus capturedStatus = argumentCaptor.getValue();

        Assert.assertThat(capturedStatus.getMessage(), is(equalTo("Its a big message")));
        Assert.assertThat(capturedStatus.getRoomId(), is(equalTo("RoomId")));
        Assert.assertThat(capturedStatus.getUserDisplayName(), is(equalTo("Example Display Name")));
        Assert.assertThat(capturedStatus.getUserId(), is(equalTo("PersonId")));

    }

    @Test
    public void processWebhook_StandupStatusRequest_MessageIsSentToRoomWithStandupStatusList() {

        Message message = new Message();
        message.setText("/standup ");
        Mockito.when(this.mockSparkApi.getMessage(Mockito.anyString(), Mockito.anyString())).thenReturn(message);

        StandupStatus standupStatus = new StandupStatus();
        standupStatus.setMessage("Example status");
        standupStatus.setUserDisplayName("Example user");

        Mockito.when(this.mockStandupService.getStandupStatusForRoom(Mockito.anyString()))
                .thenReturn(Collections.singletonList(standupStatus));

        this.service.processWebhook("RoomId", "PersonId", "MessageId");

        ArgumentCaptor<String> capturedRoomId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> capturedMessageContent = ArgumentCaptor.forClass(String.class);
        Mockito.verify(this.mockSparkApi).postMessageToRoom(capturedRoomId.capture(), capturedMessageContent.capture());

        Assert.assertThat(capturedRoomId.getValue(), is(equalTo("RoomId")));
        Assert.assertThat(capturedMessageContent.getValue(), is(equalTo("Example user: Example status\n")));
    }

    @Test
    public void processWebhook_NonStandupMessage_StandupServiceIsNotCalled() {

        Message message = new Message();
        message.setText("A message not related to a standup");
        Mockito.when(this.mockSparkApi.getMessage(Mockito.anyString(), Mockito.anyString())).thenReturn(message);

        this.service.processWebhook("RoomId", "PersonId", "MessageId");

        Mockito.verifyZeroInteractions(this.mockStandupService);
    }

    @Test
    public void updateWebhookSubscriptions_CreateWebhookForRoomWithoutWebhook_WebhookCreateCallIsMade() {

        Room exampleRoom = new Room();
        exampleRoom.setId("1234");

        Mockito.when(this.mockSparkApi.getRoomMembershipForApiUser())
                .thenReturn(Collections.singletonList(exampleRoom).iterator());

        Mockito.when(this.mockSparkApi.getWebhooksForApiUser()).thenReturn(new ArrayList<Webhook>().iterator());

        this.service.updateWebhookSubscriptions();

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> targetUrlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> resourceCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> eventCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> roomIdCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(this.mockSparkApi).registerWebhook(nameCaptor.capture()
                , targetUrlCaptor.capture()
                , resourceCaptor.capture()
                , eventCaptor.capture()
                , roomIdCaptor.capture());

        Assert.assertThat(nameCaptor.getValue(), is(equalTo("Standup Bot Webhook")));
        Assert.assertThat(targetUrlCaptor.getValue(), is(equalTo("http://example.org/webhook")));
        Assert.assertThat(resourceCaptor.getValue(), is(equalTo("messages")));
        Assert.assertThat(eventCaptor.getValue(), is(equalTo("created")));
        Assert.assertThat(roomIdCaptor.getValue(), is(equalTo("1234")));

    }

    @Test
    public void updateWebhookSubscriptions_WebhookAlreadyExists_WebhookIsNotRecreated() {
        Room exampleRoom = new Room();
        exampleRoom.setId("1234");

        Mockito.when(this.mockSparkApi.getRoomMembershipForApiUser())
                .thenReturn(Collections.singletonList(exampleRoom).iterator());

        Webhook webhook = new Webhook();
        webhook.setFilter("roomId=1234");
        webhook.setName("Standup Bot Webhook");

        Mockito.when(this.mockSparkApi.getWebhooksForApiUser())
                .thenReturn(Collections.singletonList(webhook).iterator());

        this.service.updateWebhookSubscriptions();

        Mockito.verify(this.mockSparkApi, Mockito.never()).registerWebhook(Mockito.anyString()
                , Mockito.anyString()
                , Mockito.anyString()
                , Mockito.anyString()
                , Mockito.anyString());
    }
}
