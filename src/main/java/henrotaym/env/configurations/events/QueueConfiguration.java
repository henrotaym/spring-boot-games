package henrotaym.env.configurations.events;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.enums.events.QueueName;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class QueueConfiguration {
  @Bean
  @Profile({ProfileName.QUEUE, ProfileName.TEST})
  Queue deadLetterQueue() {
    return QueueBuilder.durable(QueueName.DEFAULT_DLQ).build();
  }

  @Bean
  @Profile({ProfileName.QUEUE, ProfileName.TEST})
  Queue queue() {
    return QueueBuilder.durable(QueueName.DEFAULT)
        .deadLetterExchange("")
        .deadLetterRoutingKey(QueueName.DEFAULT_DLQ)
        .build();
  }
}
