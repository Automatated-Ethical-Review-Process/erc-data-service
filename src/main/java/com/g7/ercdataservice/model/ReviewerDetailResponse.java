package com.g7.ercdataservice.model;

import com.g7.ercdataservice.enums.ERole;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReviewerDetailResponse {

    private String reviewerId;
    private String name;
    private ERole role;
    private int assignedProposal;
}
