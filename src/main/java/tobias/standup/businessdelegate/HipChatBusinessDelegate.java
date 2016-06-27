package tobias.standup.businessdelegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tobias.standup.api.hipchat.From;
import tobias.standup.api.hipchat.HipchatRequest;
import tobias.standup.api.hipchat.HipchatResponse;
import tobias.standup.entity.StandupStatus;
import tobias.standup.service.IStandupService;

/**
 * This class provides a mapping abstraction against the HipChat API.
 * If the HipChat API changes then the only changes that will be required are in this class, and potentially the
 * controller.
 * The Service and Dao classes can remain unchanged.
 */
@Component
@Transactional
public class HipChatBusinessDelegate implements IHipChatBusinessDelegate {

    private IStandupService service;

    @Autowired
    public HipChatBusinessDelegate(IStandupService service) {
        this.service = service;
    }

    @Override
    public HipchatResponse processWebhook(HipchatRequest request) {

        HipchatResponse response = new HipchatResponse();
        response.setNotify(false);



        String standupMessage = request.getItem().getMessage().getMessage();

        if (!standupMessage.startsWith("/standup")) {
            return null;
        }

        standupMessage = standupMessage.replaceAll("/standup", "").trim();

        String roomId = Long.toString(request.getItem().getRoom().getId());

        if (standupMessage.equals("")) { // Route the message appropriately

            Iterable<StandupStatus> standupStatusList = this.service.getStandupStatusForRoom(roomId);
            StringBuilder result = new StringBuilder();
            for (StandupStatus standupStatus : standupStatusList) {
                String formatString = "<b>%s:</b> %s<br /> ";
                String value = String.format(formatString
                        , standupStatus.getUserDisplayName()
                        , standupStatus.getMessage());
                result.append(value);
            }
            response.setMessage(result.toString());

            response.setColor("yellow");
            response.setMessageFormat("html");


            return response;

        } else {
            From messageFrom = request.getItem().getMessage().getFrom();

            StandupStatus standupStatus = new StandupStatus();
            standupStatus.setMessage(standupMessage);
            standupStatus.setUserDisplayName(messageFrom.getName());
            standupStatus.setUserId(Integer.toString(messageFrom.getId()));
            standupStatus.setRoomId(Long.toString(request.getItem().getRoom().getId()));

            this.service.saveStandupStatus(standupStatus);

            response.setMessageFormat("text");
            response.setColor("green");
            response.setMessage("(caruso)");
            return response;
        }
    }

}
