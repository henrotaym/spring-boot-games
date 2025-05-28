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
    this.logPretty(dumpables, pretty -> log.info(pretty));
  }

  public void dd(Object... dumpables) throws JsonProcessingException {
    this.logPretty(dumpables, pretty -> log.error(pretty));
    System.exit(1);
  }

  private void logPretty(Object[] dumpables, Consumer<String> callback)
      throws JsonProcessingException {
    callback.accept(this.pretty(dumpables));
  }
}
