package com.arip.core.risk.rules;

import com.arip.core.risk.model.RiskContext;
import com.arip.core.risk.model.RuleResult;

import java.math.BigDecimal;

public class HighAmountTransactionRule implements RiskRule {

    @Override
    public RuleResult evaluate(RiskContext context) {
        RuleResult ruleResult = new RuleResult();

        BigDecimal amount = context.getAmount();

        if (amount == null) {
            ruleResult.setTriggered(false);
            ruleResult.setScoreImpact(0);
            return ruleResult;
        }

        int score = calculateScore(amount);

        ruleResult.setTriggered(score > 0);
        ruleResult.setScoreImpact(score);
        ruleResult.setReasonCode("HIGH_AMOUNT");

        return ruleResult;
    }

    private int calculateScore(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(10000)) <= 0) {
            return 0;
        } else if (amount.compareTo(BigDecimal.valueOf(50000)) <= 0) {
            return 10;
        } else if (amount.compareTo(BigDecimal.valueOf(100000)) <= 0) {
            return 25;
        } else {
            return 40;
        }
    }
}