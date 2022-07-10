package com.g7.ercdataservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ApiError {
    private Date timestamp = new Date();
    private List<String> fields;
    private String message;

}
