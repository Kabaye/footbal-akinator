package edu.bsu.footbal.akinator.rule.store;

import edu.bsu.footbal.akinator.rule.entity.Rule;
import java.util.List;
import lombok.Data;

@Data
public class RulesStore {
    private final List<Rule> rules;
}
