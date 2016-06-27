package tobias.standup.businessdelegate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import tobias.standup.api.ciscospark.dto.CiscoSparkWebhookRequest;
import tobias.standup.api.ciscospark.dto.Data;
import tobias.standup.service.ICiscoSparkService;

/**
 * Created by tobias on 24/06/16.
 */
public class CiscoSparkBusinessDelegateTest {

    private ICiscoSparkService mockService = Mockito.mock(ICiscoSparkService.class);

    private CiscoSparkBusinessDelegate businessDelegate;

    @Before
    public void before() {
        this.businessDelegate = new CiscoSparkBusinessDelegate(this.mockService);
    }

    @Test
    public void processWebhook_MessageExtraction_ParametersArePassedCorrectly() {

        CiscoSparkWebhookRequest request = new CiscoSparkWebhookRequest();
        Data data = new Data();
        data.setRoomId("ExampleRoomId");
        data.setPersonId("ExamplePersonId");
        data.setId("ExampleMessageId");
        request.setData(data);

        this.businessDelegate.processWebhook(request);

        ArgumentCaptor<String> roomIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> personIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageIdCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(this.mockService).processWebhook(roomIdCaptor.capture()
                , personIdCaptor.capture()
                , messageIdCaptor.capture());
    }

    @Test
    public void updateStandupWebhookSubscriptions_ServiceExecution_ServiceIsCalled() {
        this.businessDelegate.updateStandupWebhookSubscriptions();
        Mockito.verify(this.mockService, Mockito.times(1)).updateWebhookSubscriptions();
    }

}
