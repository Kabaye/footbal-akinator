package edu.bsu.footbal.akinator.app.view;

import edu.bsu.footbal.akinator.rule.entity.Rule;
import edu.bsu.footbal.akinator.rule.store.RulesStore;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Component;

/**
 * created by @Kabaye
 * date 14.11.2020
 */

@Component
public class ViewController {
    private final RulesStore rulesStore;
    private final JFrame jFrame;

    public ViewController(RulesStore rulesStore) {
        this.rulesStore = rulesStore;
        jFrame = new JFrame();
        jFrame.setVisible(false);
        jFrame.setAlwaysOnTop(true);
    }


    public void showAllRules() {
        JList<Rule> jList = new JList<>(rulesStore.getRules().toArray(new Rule[0]));
        jList.setPreferredSize(new Dimension(650, 320));
        JOptionPane.showMessageDialog(jFrame, jList, "All rules", JOptionPane.INFORMATION_MESSAGE);
    }

    public int showMainMenu() {
        String[] buttons = {"Show rules", "Start game", "Exit game"};
        return JOptionPane.showOptionDialog(jFrame, "Football Akinator © Kabaye Inc.", "Main Menu",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
    }
}
