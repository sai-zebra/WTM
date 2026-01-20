package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;
import java.util.Map;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * SurveyResponseCreate
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-19T21:19:59.650180600+05:30[Asia/Calcutta]", comments = "Generator version: 7.8.0")
public class SurveyResponseCreate {

  @Valid
  private Map<String, Object> responses = new HashMap<>();

  public SurveyResponseCreate responses(Map<String, Object> responses) {
    this.responses = responses;
    return this;
  }

  public SurveyResponseCreate putResponsesItem(String key, Object responsesItem) {
    if (this.responses == null) {
      this.responses = new HashMap<>();
    }
    this.responses.put(key, responsesItem);
    return this;
  }

  /**
   * Get responses
   * @return responses
   */
  
  @Schema(name = "responses", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("responses")
  public Map<String, Object> getResponses() {
    return responses;
  }

  public void setResponses(Map<String, Object> responses) {
    this.responses = responses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SurveyResponseCreate surveyResponseCreate = (SurveyResponseCreate) o;
    return Objects.equals(this.responses, surveyResponseCreate.responses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SurveyResponseCreate {\n");
    sb.append("    responses: ").append(toIndentedString(responses)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

