package tobias.standup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tobias.standup.api.hipchat.HipchatRequest;
import tobias.standup.api.hipchat.HipchatResponse;
import tobias.standup.businessdelegate.IHipChatBusinessDelegate;

/**
 * Created by tobias on 7/06/16.
 */
@Controller
public class HipChatStandupController {

    private IHipChatBusinessDelegate businessDelegate;

    @Autowired
    public HipChatStandupController(IHipChatBusinessDelegate businessDelegate) {
        this.businessDelegate = businessDelegate;
    }

    @RequestMapping(value="/hipchat-standup", method = RequestMethod.POST)
    public @ResponseBody HipchatResponse standup(@RequestBody HipchatRequest input) throws Exception {
        return this.businessDelegate.processWebhook(input);
    }

}
