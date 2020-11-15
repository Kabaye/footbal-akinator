package edu.bsu.footbal.akinator.rule.entity;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Rule {
    private final Set<RuleEntity> ifRules;
    private final RuleEntity then;

    @Override
    public String toString() {
        StringJoiner ruleJoiner = new StringJoiner("", "<html><font color=red>If</font> ", ";");
        Iterator<RuleEntity> iterator = ifRules.iterator();
        RuleEntity firstIfRuleEntity = iterator.next();
        ruleJoiner.add(firstIfRuleEntity.getAttribute())
                .add(" <html><font color=red>=</font> ")
                .add(firstIfRuleEntity.getValue());
        while (iterator.hasNext()) {
            RuleEntity next = iterator.next();
            ruleJoiner.add(" <html><font color=red>and</font> ")
                    .add(next.getAttribute())
                    .add(" <html><font color=red>=</font> ")
                    .add(next.getValue());
        }
        ruleJoiner.add(" <html><font color=red>then</font> ")
                .add(then.getAttribute())
                .add(" <html><font color=red>=</font> ")
                .add(then.getValue());
        return ruleJoiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(ifRules, rule.ifRules) &&
                Objects.equals(then, rule.then);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ifRules, then);
    }
}
