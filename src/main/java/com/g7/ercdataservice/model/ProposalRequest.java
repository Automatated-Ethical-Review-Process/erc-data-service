package com.g7.ercdataservice.model;

import com.g7.ercdataservice.enums.ProposalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalRequest {

    private String name;
    private ProposalType type;

}
