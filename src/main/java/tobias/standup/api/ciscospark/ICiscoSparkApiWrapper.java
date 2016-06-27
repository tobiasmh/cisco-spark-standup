package tobias.standup.api.ciscospark;

import tobias.standup.api.ciscospark.sdk.Message;
import tobias.standup.api.ciscospark.sdk.Person;
import tobias.standup.api.ciscospark.sdk.Room;
import tobias.standup.api.ciscospark.sdk.Webhook;

import java.util.Iterator;

/**
 * Created by tobias on 24/06/16.
 */
public interface ICiscoSparkApiWrapper {

    Message getMessage(String messageId, String roomId);

    Person getPerson(String personId);

    Room getRoom(String roomId);

    Iterator<Room> getRoomMembershipForApiUser();

    void postMessageToRoom(String roomId, String message);

    Iterator<Webhook> getWebhooksForApiUser();

    void registerWebhook(String name, String targetUrl, String resource, String event, String roomId);
}