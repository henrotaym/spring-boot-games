package henrotaym.env.queues.events;

import henrotaym.env.enums.EventName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserCreatedEvent implements Event {
  private String message;
  public static final String EVENT_NAME = EventName.USER_CREATED;

  @Override
  public String eventName() {
    return EVENT_NAME;
  }
}
