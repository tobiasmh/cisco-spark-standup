package tobias.standup.businessdelegate;


import tobias.standup.api.ciscospark.dto.CiscoSparkWebhookRequest;

/**
 * Created by tobias on 24/06/16.
 */
public interface ICiscoSparkBusinessDelegate {

    void processWebhook(CiscoSparkWebhookRequest request);

    void updateStandupWebhookSubscriptions();

}
