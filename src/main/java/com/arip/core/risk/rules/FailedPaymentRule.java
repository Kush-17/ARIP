package com.arip.core.risk.rules;

import com.arip.core.risk.model.RiskContext;
import com.arip.core.risk.model.RuleResult;

public class FailedPaymentRule implements RiskRule{
    @Override
    public RuleResult evaluate(RiskContext context){
        RuleResult ruleResult = new RuleResult();

        if(context.isPinVerified()){
            ruleResult.setTriggered(false);
            ruleResult.setScoreImpact(0);
            return ruleResult;
        }

        int failures = context.getFailedAttempts() != null ? context.getFailedAttempts() : 0 ;
        int score = calculateScore(failures);

        ruleResult.setScoreImpact(score);

        if (score > 0) {
            ruleResult.setTriggered(true);
            ruleResult.setReasonCode("FAILED_PAYMENT_VELOCITY");
        } else {
            ruleResult.setTriggered(false);
        }

        return ruleResult;
    }

    private int calculateScore (int failures){
        if (failures <= 1) {
            return 0;
        } else if (failures <= 3) {
            return 10;
        } else if (failures == 4) {
            return 25;
        } else {
            return 40;
        }
    }
}
