package edu.bsu.footbal.akinator.view;

import edu.bsu.footbal.akinator.rule.entity.Rule;
import edu.bsu.footbal.akinator.rule.store.RulesStore;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JOptionPane;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * created by @Kabaye
 * date 14.11.2020
 */

@Component
@RequiredArgsConstructor
public class ViewController {
    private final RulesStore rulesStore;

    public void showAllRules() {
        JList<Rule> jList = new JList<>(rulesStore.getRules().toArray(new Rule[0]));
        jList.setPreferredSize(new Dimension(650, 320));
        JOptionPane.showMessageDialog(null, jList);
    }
}
