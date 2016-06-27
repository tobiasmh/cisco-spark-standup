package tobias.standup.service;

/**
 * Created by tobias on 24/06/16.
 */
public interface ICiscoSparkService {

    void processWebhook(String roomId, String personId, String messageId);

    void updateWebhookSubscriptions();

}
