package tobias.standup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tobias.standup.api.ciscospark.dto.CiscoSparkWebhookRequest;
import tobias.standup.businessdelegate.ICiscoSparkBusinessDelegate;

/**
 * Created by tobias on 24/06/16.
 */
@Controller
public class CiscoSparkStandupController {

    private ICiscoSparkBusinessDelegate businessDelegate;

    @Autowired
    public CiscoSparkStandupController(ICiscoSparkBusinessDelegate businessDelegate) {
        this.businessDelegate = businessDelegate;
    }

    @RequestMapping("/cisco-spark-standup")
    public ResponseEntity<?> standupWebhook(@RequestBody CiscoSparkWebhookRequest request) {
        this.businessDelegate.processWebhook(request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping("/cisco-spark-standup-webhook-refresh")
    public ResponseEntity<?> updateStandupWebhookSubscriptions() {
        this.businessDelegate.updateStandupWebhookSubscriptions();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Scheduled(fixedDelay = 60000)
    public void updateStandupWebhookSubscriptionsScheduler() {
        this.businessDelegate.updateStandupWebhookSubscriptions();
    }

}
