package tobias.standup.controller;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tobias.standup.businessdelegate.ICiscoSparkBusinessDelegate;
import tobias.standup.businessdelegate.IHipChatBusinessDelegate;

/**
 * Created by tobias on 25/06/16.
 */

@Profile("test")
@Configuration
@EnableWebMvc
public class ControllerTestConfiguration {

    @Bean
    public IHipChatBusinessDelegate hipChatBusinessDelegate() {
        return Mockito.mock(IHipChatBusinessDelegate.class);
    }

    @Bean
    public ICiscoSparkBusinessDelegate ciscoSparkBusinessDelegate() { return Mockito.mock(ICiscoSparkBusinessDelegate.class); }

    @Bean
    public HipChatStandupController hipChatStandupController() {
        return new HipChatStandupController(hipChatBusinessDelegate());
    }

    @Bean
    public CiscoSparkStandupController ciscoSparkStandupController() {
        return new CiscoSparkStandupController(ciscoSparkBusinessDelegate());
    }
}
