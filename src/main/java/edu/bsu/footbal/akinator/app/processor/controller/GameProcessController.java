package edu.bsu.footbal.akinator.app.processor.controller;

import edu.bsu.footbal.akinator.app.view.ViewController;
import edu.bsu.footbal.akinator.rule.entity.Rule;
import edu.bsu.footbal.akinator.rule.entity.RuleEntity;
import edu.bsu.footbal.akinator.rule.entity.RuleStatus;
import edu.bsu.footbal.akinator.rule.store.RulesStore;
import edu.bsu.footbal.akinator.utils.FourthConsumer;
import edu.bsu.footbal.akinator.utils.FunctionsMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * created by @Kabaye
 * date 15.11.2020
 */

@Component
@RequiredArgsConstructor
public class GameProcessController {
    private final ViewController viewController;
    private final RulesStore rulesStore;

    private final FunctionsMap<RuleStatus, FourthConsumer<Rule, Set<RuleEntity>, Stack<String>, Set<String>>> rulesFunctionsMap = new FunctionsMap<>();

    @PostConstruct
    public void postConstruct() {
        rulesFunctionsMap.put(RuleStatus.TRUE, this::trueRule)
                .put(RuleStatus.FALSE, (first, second, third, fourth) -> {
                })
                .put(RuleStatus.INDEFINITE, this::indefiniteRule);
    }

    public String startNewGame() {
        Stack<String> targets = new Stack<>();
        targets.push("Club");
        targets.push("Country");
        targets.push("Tournament");
        Set<RuleEntity> context = new HashSet<>();
        RuleEntity target = findTarget(targets, new HashSet<>(targets), context);
        return target.getValue();
    }

    private RuleEntity findTarget(Stack<String> targets, Set<String> targetsSet, Set<RuleEntity> context) {
        while (!targets.isEmpty()) {
            String target = targets.peek();
            String result;
            List<Integer> rulesWithCorrectTarget = findFirstRuleWithTarget(target);
            if (rulesWithCorrectTarget.isEmpty() || (context.stream()
                    .anyMatch(ruleEntity -> ruleEntity.getValue().equals("UEFA Champions League")
                            && ruleEntity.getAttribute().equals("Tournament")) && target.equals("Country"))) {
                result = viewController.askUser(target, context);
                context.add(new RuleEntity(target, result));
                String pop = targets.pop();
                targetsSet.remove(pop);
                continue;
            }
            boolean allRulesAreFalse = true;
            for (Integer ruleIndex : rulesWithCorrectTarget) {
                Rule rule = rulesStore.getRule(ruleIndex);
                RuleStatus ruleStatus = ruleStatus(rule, context);
                rulesFunctionsMap.get(ruleStatus).accept(rule, context, targets, targetsSet);
                if (ruleStatus.equals(RuleStatus.INDEFINITE) || ruleStatus.equals(RuleStatus.TRUE)) {
                    allRulesAreFalse = false;
                    break;
                }
            }
            if (allRulesAreFalse) {
                throw new RuntimeException("We can't find club with such parameters");
            }
            if (context.stream().anyMatch(ruleEntity -> ruleEntity.getAttribute().equals(targets.peek()))) {
                String targetTemp = targets.pop();
                targetsSet.remove(targetTemp);
                if (targets.isEmpty()) {
                    return context.stream()
                            .filter(ruleEntity -> ruleEntity.getAttribute().equals(targetTemp))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Targets is empty but processor didn't find anything..."));
                }
            }
        }
        throw new RuntimeException("Something went wrong!");
    }

    public void trueRule(Rule rule, Set<RuleEntity> context, Stack<String> targets, Set<String> targetsSet) {
        Optional<RuleEntity> first = context.stream().filter(ruleEntity -> rule.getThen().getAttribute().equals(ruleEntity.getAttribute()))
                .findFirst();
        if (first.isPresent() && !first.get().getValue().equals(rule.getThen().getValue())) {
            throw new RuntimeException("Something went wrong");
        }
        context.add(rule.getThen());
    }

    public void indefiniteRule(Rule rule, Set<RuleEntity> context, Stack<String> targets, Set<String> targetsSet) {
        for (RuleEntity ruleEntity : rule.getIfRules()) {
            if (!context.contains(ruleEntity)) {
                if (!targetsSet.contains(ruleEntity.getAttribute())) {
                    targets.push(ruleEntity.getAttribute());
                    targetsSet.add(ruleEntity.getAttribute());
                }
            }
        }
    }

    public List<Integer> findFirstRuleWithTarget(String target) {
        List<Integer> correctIndexes = new ArrayList<>();
        for (int i = 0; i < rulesStore.getRules().size(); i++) {
            if (rulesStore.getRule(i).getThen().getAttribute().equals(target)) {
                correctIndexes.add(i);
            }
        }
        return correctIndexes;
    }

    public RuleStatus ruleStatus(Rule ruleToCheck, Set<RuleEntity> context) {
        if (context.containsAll(ruleToCheck.getIfRules())) {
            return RuleStatus.TRUE;
        } else {
            for (RuleEntity ruleEntity : ruleToCheck.getIfRules()) {
                for (RuleEntity contextEntity : context) {
                    if (ruleEntity.getAttribute().equals(contextEntity.getAttribute())
                            && !ruleEntity.getValue().equals(contextEntity.getValue())) {
                        return RuleStatus.FALSE;
                    }
                }
            }
        }
        return RuleStatus.INDEFINITE;
    }

}
