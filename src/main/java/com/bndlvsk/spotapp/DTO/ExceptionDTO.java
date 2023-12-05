package com.bndlvsk.spotapp.DTO;

import lombok.*;

@Value
public class ExceptionDTO {
    int status;
    String message;
    long timestamp;
}
