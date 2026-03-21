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
    }
}
