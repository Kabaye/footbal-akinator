package edu.bsu.footbal.akinator.rule.store;

import edu.bsu.footbal.akinator.rule.entity.Rule;
import edu.bsu.footbal.akinator.rule.entity.RuleEntity;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

public class RulesStore {
    @Getter
    private final Set<Rule> rules;
    private final List<Rule> rulesList;
    @Getter
    private final Map<String, List<String>> possibleParams;

    public RulesStore(Set<Rule> rules) {
        this.rules = rules;
        rulesList = List.copyOf(rules);

        possibleParams = rules.stream()
                .flatMap(rule -> Stream.concat(Stream.of(rule.getThen()), rule.getIfRules().stream()))
                .distinct()
                .collect(Collectors.groupingBy(RuleEntity::getAttribute,
                        Collectors.mapping(RuleEntity::getValue, Collectors.toList())));
    }

    public Rule getRule(int index) {
        return rulesList.get(index);
    }

    public int size() {
        return rules.size();
    }
}
