package henrotaym.env.utils.api;

import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

@Component
@RequiredArgsConstructor
@Setter
@Getter
public class JsonResponse {
  private ResultActions response;

  public JsonResponse status(Function<StatusResultMatchers, ResultMatcher> callback)
      throws Exception {
    this.response.andExpect(callback.apply(MockMvcResultMatchers.status()));

    return this;
  }

  public JsonResponse content(
      String expression, Function<JsonPathResultMatchers, ResultMatcher> callback)
      throws Exception {
    this.response.andExpect(callback.apply(MockMvcResultMatchers.jsonPath(expression)));

    return this;
  }

  public ResultActions print() throws Exception {
    return this.response.andDo(MockMvcResultHandlers.print());
  }
}
