package henrotaym.env.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import henrotaym.env.utils.debug.DebugLogger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Debug {
  public static DebugLogger logger() {
    return new DebugLogger(new ObjectMapper());
  }
}
