package edu.bsu.footbal.akinator.rule.entity;

import java.util.List;
import java.util.StringJoiner;
import lombok.Data;
import lombok.experimental.Accessors;

@Data(staticConstructor = "of")
@Accessors(chain = true)
public class Rule {
    private final List<RuleEntity> ifRules;
    private final RuleEntity then;

    @Override
    public String toString() {
        StringJoiner ruleJoiner = new StringJoiner("", "<html><font color=red>If</font> ", ";");
        RuleEntity firstIfRuleEntity = ifRules.get(0);
        ruleJoiner.add(firstIfRuleEntity.getAttribute())
                .add(" <html><font color=red>=</font> ")
                .add(firstIfRuleEntity.getValue());
        for (int i = 1; i < ifRules.size(); i++) {
            ruleJoiner.add(" <html><font color=red>and</font> ")
                    .add(ifRules.get(i).getAttribute())
                    .add(" <html><font color=red>=</font> ")
                    .add(ifRules.get(i).getValue());
        }
        ruleJoiner.add(" <html><font color=red>then</font> ")
                .add(then.getAttribute())
                .add(" <html><font color=red>=</font> ")
                .add(then.getValue());
        return ruleJoiner.toString();
    }
}
