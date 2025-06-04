package henrotaym.env.annotations.queues.listeners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RetryableTopic
@KafkaListener
public @interface RetryableKafkaListener {

  @AliasFor(annotation = KafkaListener.class, attribute = "topics")
  String[] value() default {};

  @AliasFor(annotation = RetryableTopic.class, attribute = "attempts")
  String attempts() default "1";
}
