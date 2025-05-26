package henrotaym.env.utils.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Component
@RequiredArgsConstructor
public class JsonRequest {
  @Setter private MockHttpServletRequestBuilder request;
  private final ObjectMapper objectMapper;

  public JsonRequest get(String uriTemplate, Object... uriVariables) {
    return this.verb(MockMvcRequestBuilders.get(uriTemplate, uriVariables));
  }

  public JsonRequest put(String uriTemplate, Object... uriVariables) {
    return this.verb(MockMvcRequestBuilders.put(uriTemplate, uriVariables));
  }

  public JsonRequest post(String uriTemplate, Object... uriVariables) {
    return this.verb(MockMvcRequestBuilders.post(uriTemplate, uriVariables));
  }

  public JsonRequest delete(String uriTemplate, Object... uriVariables) {
    return this.verb(MockMvcRequestBuilders.delete(uriTemplate, uriVariables));
  }

  public JsonRequest content(Object content) throws JsonProcessingException {
    this.request.content(this.objectMapper.writeValueAsString(content));

    return this;
  }

  public JsonRequest included(String... included) {
    return this.queryParam("included", included);
  }

  public JsonRequest queryParam(String name, String... values) {
    this.request.queryParam(name, values);

    return this;
  }

  protected JsonRequest verb(MockHttpServletRequestBuilder verbRequest) {
    this.setRequest(verbRequest);

    return this.json();
  }

  protected JsonRequest json() {
    this.request.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

    return this;
  }

  public MockHttpServletRequestBuilder request() {
    return this.request;
  }
}
