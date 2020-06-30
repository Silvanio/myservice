package com.myservice.common.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
public class ErrorDTO {
  private Date timestamp;
  private String message;
  private String details;
  private String field;

  public ErrorDTO(Date timestamp, String message, String details) {
    super();
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
  }

}