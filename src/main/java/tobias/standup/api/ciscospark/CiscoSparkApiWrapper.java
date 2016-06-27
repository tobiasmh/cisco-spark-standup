package tobias.standup.api.ciscospark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tobias.standup.api.ciscospark.sdk.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

/**
 * Created by tobias on 25/06/16.
 */
@Component
public class CiscoSparkApiWrapper implements ICiscoSparkApiWrapper {

    private Spark spark;

    // TODO Need to make sure this sdk from Cisco is actually thread safe
    @Autowired
    public CiscoSparkApiWrapper(Spark spark) {
        this.spark = spark;
    }

    @Override
    public Message getMessage(String messageId, String roomId) {
        return this.spark.messages().path("/"+messageId).get();
    }

    @Override
    public Person getPerson(String personId) {
        return this.spark.people().path("/"+personId).get();
    }

    @Override
    public Room getRoom(String roomId) {
        return this.spark.rooms().queryParam("roomId", roomId).get();
    }

    @Override
    public Iterator<Room> getRoomMembershipForApiUser() {
        return this.spark.rooms().iterate();
    }

    @Override
    public void postMessageToRoom(String roomId, String messageString) {
        Message message = new Message();
        message.setRoomId(roomId);
        message.setText(messageString);
        this.spark.messages().post(message);
    }

    @Override
    public Iterator<Webhook> getWebhooksForApiUser() {
        return this.spark.webhooks().iterate();
    }

    @Override
    public void registerWebhook(String name, String targetUrl, String resource, String event, String roomId) {

        Webhook webhook = new Webhook();
        webhook.setName(name);
        try {
            webhook.setTargetUrl(new URI(targetUrl));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        webhook.setResource(resource);
        webhook.setEvent(event);
        webhook.setFilter(String.format("roomId=%s", roomId));
        this.spark.webhooks().post(webhook);

    }
}
