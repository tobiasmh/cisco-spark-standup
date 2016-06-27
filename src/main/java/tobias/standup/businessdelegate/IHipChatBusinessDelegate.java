package tobias.standup.businessdelegate;

import tobias.standup.api.hipchat.HipchatRequest;
import tobias.standup.api.hipchat.HipchatResponse;

/**
 * Created by tobias on 24/06/16.
 */
public interface IHipChatBusinessDelegate {

    HipchatResponse processWebhook(HipchatRequest request);
}
