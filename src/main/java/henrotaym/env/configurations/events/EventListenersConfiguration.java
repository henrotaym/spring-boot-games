package henrotaym.env.configurations.events;

import henrotaym.env.dto.events.EventListenersMapping;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.factories.dto.events.EventListenersMappingFactory;
import henrotaym.env.listeners.UserCreatedListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class EventListenersConfiguration {
  @Bean
  @Profile({ProfileName.QUEUE, ProfileName.TEST})
  EventListenersMapping eventListenersMapping(
      EventListenersMappingFactory factory, UserCreatedListener userCreatedListener) {
    EventListenersMapping eventListenersMapping = factory.create(userCreatedListener);

    return eventListenersMapping;
  }
}
