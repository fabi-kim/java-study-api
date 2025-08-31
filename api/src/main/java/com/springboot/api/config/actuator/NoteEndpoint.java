package com.springboot.api.config.actuator;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "note")
public class NoteEndpoint {

  private final Map<String, Object> noteContent = new HashMap<>();

  @ReadOperation
  public Map<String, Object> getNoteContent() {
    return noteContent;
  }

  @WriteOperation
  public Map<String, Object> setNoteContent(String key, Object value) {
    noteContent.put(key, value);
    return noteContent;
  }

}
