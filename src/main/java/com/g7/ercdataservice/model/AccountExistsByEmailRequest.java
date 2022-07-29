package com.g7.ercdataservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Service
@ToString
public class AccountExistsByEmailRequest {

    private Set<String> emails;
}
