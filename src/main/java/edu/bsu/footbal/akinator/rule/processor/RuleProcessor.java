package edu.bsu.footbal.akinator.rule.processor;

import edu.bsu.footbal.akinator.rule.store.RulesStore;
import edu.bsu.footbal.akinator.view.ViewController;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * created by @Kabaye
 * date 14.11.2020
 */

@Service
@RequiredArgsConstructor
public class RuleProcessor {
    private final ViewController viewController;
    private final RulesStore rulesStore;

    @PostConstruct
    public void startProcess() {

    }
}
