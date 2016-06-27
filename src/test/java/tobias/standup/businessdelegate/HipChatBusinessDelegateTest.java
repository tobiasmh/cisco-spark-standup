package tobias.standup.businessdelegate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import tobias.standup.api.hipchat.*;
import tobias.standup.entity.StandupStatus;
import tobias.standup.service.IStandupService;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

/**
 * Created by tobias on 24/06/16.
 */
public class HipChatBusinessDelegateTest {

    private IStandupService serviceMock = Mockito.mock(IStandupService.class);

    private HipChatBusinessDelegate businessDelegate;

    @Before
    public void before() {
        this.businessDelegate = new HipChatBusinessDelegate(this.serviceMock);
    }

    private HipchatRequest getMockRequest() {

        HipchatRequest mockRequest = Mockito.mock(HipchatRequest.class);
        Item mockItem = Mockito.mock(Item.class);
        Mockito.when(mockRequest.getItem()).thenReturn(mockItem);

        Room mockRoom = Mockito.mock(Room.class);
        Mockito.when(mockItem.getRoom()).thenReturn(mockRoom);
        Mockito.when(mockRoom.getId()).thenReturn(5678);

        Message mockMessage = Mockito.mock(Message.class);
        Mockito.when(mockItem.getMessage()).thenReturn(mockMessage);

        From mockFrom = Mockito.mock(From.class);
        Mockito.when(mockMessage.getFrom()).thenReturn(mockFrom);

        Mockito.when(mockFrom.getId()).thenReturn(1234);
        Mockito.when(mockFrom.getName()).thenReturn("Mike Ross");

        return mockRequest;
    }

    @Test
    public void processWebhook_BusinessDelegateCalledWithNewStandupStatus_RequestParametersArePassedCorrectly() {

        HipchatRequest mockRequest = getMockRequest();

        Mockito.when(mockRequest.getItem().getMessage().getMessage()).thenReturn("/standup Example status update");

        this.businessDelegate.processWebhook(mockRequest);

        ArgumentCaptor<StandupStatus> capturedArgument = ArgumentCaptor.forClass(StandupStatus.class);
        Mockito.verify(this.serviceMock).saveStandupStatus(capturedArgument.capture());

        StandupStatus capturedStandupStatus = capturedArgument.getValue();
        Assert.assertThat(capturedStandupStatus.getMessage(), is(equalTo("Example status update")));
        Assert.assertThat(capturedStandupStatus.getUserId(), is(equalTo("1234")));
        Assert.assertThat(capturedStandupStatus.getUserDisplayName(), is(equalTo("Mike Ross")));
        Assert.assertThat(capturedStandupStatus.getRoomId(), is(equalTo("5678")));

    }

    @Test
    public void processWebhook_BusinessDelegateCalledWithNewStandupStatus_CorrectResponseIsGenerated() {

        HipchatRequest mockRequest = getMockRequest();

        Mockito.when(mockRequest.getItem().getMessage().getMessage()).thenReturn("/standup Example status update");

        HipchatResponse response = this.businessDelegate.processWebhook(mockRequest);

        Assert.assertThat(response.getMessageFormat(), is(equalTo("text")));
        Assert.assertThat(response.getColor(), is(equalTo("green")));
        Assert.assertThat(response.getMessage(), is(equalTo("(caruso)")));

    }

    @Test
    public void processWebhook_BusinessDelegateCalledWithNoMessage_ServiceIsCorrectlyCalled() {

        HipchatRequest mockRequest = getMockRequest();

        Mockito.when(mockRequest.getItem().getMessage().getMessage()).thenReturn("/standup ");

        Mockito.when(this.serviceMock.getStandupStatusForRoom(Mockito.anyString())).thenReturn(new ArrayList<>());

        this.businessDelegate.processWebhook(mockRequest);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(this.serviceMock).getStandupStatusForRoom(argumentCaptor.capture());

        Assert.assertThat(argumentCaptor.getValue(), is(equalTo("5678")));

    }

    @Test
    public void processWebhook_BusinessDelegateCalledWithNoMessage_CorrectResponseIsGenerated() {

        HipchatRequest mockRequest = getMockRequest();

        Mockito.when(mockRequest.getItem().getMessage().getMessage()).thenReturn("/standup   ");

        StandupStatus mockStandupStatus = Mockito.mock(StandupStatus.class);
        Mockito.when(mockStandupStatus.getMessage()).thenReturn("Example status");
        Mockito.when(mockStandupStatus.getUserDisplayName()).thenReturn("Example user");
        Iterable<StandupStatus> mockResultList = Collections.singletonList(mockStandupStatus);

        Mockito.when(this.serviceMock.getStandupStatusForRoom(Mockito.anyString())).thenReturn(mockResultList);

        HipchatResponse response = this.businessDelegate.processWebhook(mockRequest);

        Assert.assertThat(response.getMessageFormat(), is(equalTo("html")));
        Assert.assertThat(response.getColor(), is(equalTo("yellow")));
        Assert.assertThat(response.getMessage(), is(equalTo("<b>Example user:</b> Example status<br /> ")));

    }

    @Test
    public void processWebhook_MessageWithoutStandupFlag_NullReturned() {

        HipchatRequest mockRequest = getMockRequest();

        Mockito.when(mockRequest.getItem().getMessage().getMessage()).thenReturn("An example message");


        HipchatResponse response = this.businessDelegate.processWebhook(mockRequest);

        Assert.assertThat(response, is(nullValue()));

        Mockito.verifyZeroInteractions(this.serviceMock);

    }

}