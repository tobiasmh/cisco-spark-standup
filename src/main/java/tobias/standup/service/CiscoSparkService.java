package tobias.standup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tobias.standup.api.ciscospark.ICiscoSparkApiWrapper;
import tobias.standup.api.ciscospark.sdk.Message;
import tobias.standup.api.ciscospark.sdk.Person;
import tobias.standup.api.ciscospark.sdk.Webhook;
import tobias.standup.entity.StandupStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tobias on 25/06/16.
 */
@Service
public class CiscoSparkService implements ICiscoSparkService {

    private IStandupService standupService;

    private ICiscoSparkApiWrapper sparkApi;

    @Value("${ciscoSparkWebhookTargetUrl:InvalidDefaultKey}")
    private String webhookTargetUrl;
    public void setWebhookTargetUrl(String webhookTargetUrl) {
        this.webhookTargetUrl = webhookTargetUrl;
    }

    @Autowired
    public CiscoSparkService(IStandupService standupService, ICiscoSparkApiWrapper sparkApi) {
        this.standupService = standupService;
        this.sparkApi = sparkApi;
    }

    @Override
    public void processWebhook(String roomId, String personId, String messageId) {

        Message message = this.sparkApi.getMessage(messageId, roomId);
        if (!message.getText().startsWith("/standup")) {
            return;
        }
        String messageText = message.getText().replaceAll("/standup", "").trim();
        if (messageText.equals("")) { // A standup status report has been requested
            Iterable<StandupStatus> standupStatusList = this.standupService.getStandupStatusForRoom(roomId);
            StringBuilder statusReportStringBuilder = new StringBuilder();
            for (StandupStatus standupStatus : standupStatusList) {
                String formatString = "%s: %s\n";
                String value = String.format(formatString
                        , standupStatus.getUserDisplayName()
                        , standupStatus.getMessage());
                statusReportStringBuilder.append(value);
            }
            this.sparkApi.postMessageToRoom(roomId, statusReportStringBuilder.toString());

        } else { // A new standup status is being posted to the room
            Person person = this.sparkApi.getPerson(personId);

            StandupStatus standupStatus = new StandupStatus();
            standupStatus.setUserDisplayName(person.getDisplayName());
            standupStatus.setMessage(messageText);
            standupStatus.setRoomId(roomId);
            standupStatus.setUserId(person.getId());
            this.standupService.saveStandupStatus(standupStatus);
        }
    }

    @Override
    public synchronized void updateWebhookSubscriptions() {

        Map<String, Webhook> webhookMap = new HashMap<>();

        this.sparkApi.getWebhooksForApiUser().forEachRemaining(webhook -> {
            String roomId = webhook.getFilter().replaceAll("roomId=", "");
            webhookMap.put(roomId, webhook);
        });

        this.sparkApi.getRoomMembershipForApiUser().forEachRemaining(room -> {
            if (!webhookMap.containsKey(room.getId())) {
                this.sparkApi.registerWebhook("Standup Bot Webhook", this.webhookTargetUrl, "messages", "created",
                        room.getId());
            }
        });

    }

}
