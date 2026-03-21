package com.arip.core.risk.rules;


import com.arip.core.risk.model.RiskContext;
import com.arip.core.risk.model.RuleResult;

public interface RiskRule {
    RuleResult evaluate(RiskContext context);
}
