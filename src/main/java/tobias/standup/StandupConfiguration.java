package tobias.standup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobias.standup.api.ciscospark.sdk.Spark;

import java.net.URI;

/**
 * Created by tobias on 25/06/16.
 */
@Configuration
public class StandupConfiguration {

    @Value("${ciscoSparkApiKey:InvalidDefaultKey}")
    private String apiKey;

    @Bean
    public Spark sparkApi() {
        return Spark.builder()
                .baseUrl(URI.create("https://api.ciscospark.com/v1"))
                .accessToken(this.apiKey)
                .build();
    }

}
