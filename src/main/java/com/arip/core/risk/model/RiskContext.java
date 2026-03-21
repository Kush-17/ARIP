package com.arip.core.risk.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class RiskContext {

    // Requested Data of Every Risk Rule
    private String userId;

    private BigDecimal amount;

    private String deviceFingerprint;

    private String currentCity;
    private String currentCountry;

    private LocalDateTime timestamp;

    // Derived / System Data

    private Integer failedAttempts;
    private Integer recentTransactionCount;

    private String lastKnownCity;
    private String lastKnownCountry;

    private Set<String> trustedDevices;

    private boolean pinVerified;
}
