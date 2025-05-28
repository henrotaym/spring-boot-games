package henrotaym.env.debug;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DebugLogger {
  private final ObjectMapper objectMapper;

  public String pretty(Object... dumpables) throws JsonProcessingException {
    return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dumpables);
  }

  public void dump(Object... dumpables) throws JsonProcessingException {
    this.logPretty(pretty -> log.info(pretty), dumpables);
  }

  public void dd(Object... dumpables) throws JsonProcessingException {
    this.logPretty(pretty -> log.error(pretty), dumpables);
    System.exit(1);
  }

  private void logPretty(Consumer<String> callback, Object... dumpables)
      throws JsonProcessingException {
    callback.accept(this.pretty(dumpables));
  }
}
