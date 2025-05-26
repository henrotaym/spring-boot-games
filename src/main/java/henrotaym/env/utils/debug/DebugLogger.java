package henrotaym.env.utils.debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DebugLogger {
  private final ObjectMapper objectMapper;

  public void dump(Object... dumpable) {
    this.logPretty(dumpable, (pretty) -> log.info(pretty));
  }

  public void dd(Object... dumpable) {
    this.logPretty(dumpable, (pretty) -> log.error(pretty));
    System.exit(1);
  }

  private void logPretty(Object[] dumpables, Consumer<String> callback) {
    try {
      String pretty =
          this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dumpables);
      callback.accept(pretty);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
