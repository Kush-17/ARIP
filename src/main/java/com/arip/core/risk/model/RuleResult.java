package com.arip.core.risk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleResult {
    private int scoreImpact;
    private String reasonCode;
    private boolean triggered;
}
