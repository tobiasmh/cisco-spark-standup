package tobias.standup.businessdelegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tobias.standup.api.ciscospark.dto.CiscoSparkWebhookRequest;
import tobias.standup.api.ciscospark.dto.Data;
import tobias.standup.service.ICiscoSparkService;

/**
 * Created by tobias on 24/06/16.
 */
@Component
@Transactional
public class CiscoSparkBusinessDelegate implements ICiscoSparkBusinessDelegate {

    private ICiscoSparkService service;

    @Autowired
    public CiscoSparkBusinessDelegate(ICiscoSparkService service) {
        this.service = service;
    }

    @Override
    public void processWebhook(CiscoSparkWebhookRequest request) {
        Data data = request.getData();
        this.service.processWebhook(data.getRoomId(), data.getPersonId(), data.getId());
    }

    @Override
    public void updateStandupWebhookSubscriptions() {
        this.service.updateWebhookSubscriptions();
    }

}
