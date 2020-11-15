package edu.bsu.footbal.akinator.app.view;

import edu.bsu.footbal.akinator.rule.entity.Rule;
import edu.bsu.footbal.akinator.rule.entity.RuleEntity;
import edu.bsu.footbal.akinator.rule.store.RulesStore;
import edu.bsu.footbal.akinator.utils.ResourceLoader;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Set;
import java.util.StringJoiner;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
        jList.setPreferredSize(new Dimension(730, 380));
        JOptionPane.showMessageDialog(jFrame, jList, "All rules", JOptionPane.INFORMATION_MESSAGE);
    }

    public int showMainMenu() {
        String[] buttons = {"Show rules", "Start game", "Exit game"};
        return JOptionPane.showOptionDialog(jFrame, "Football Akinator © Kabaye Inc.", "Main Menu",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
    }

    public void showResult(String clubName) {
        Image image = ResourceLoader.getImage("icons/resized/" + clubName + ".png");
        JOptionPane.showMessageDialog(jFrame, new ImageIcon(image), "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public String askUser(String target, Set<RuleEntity> context) {
        JPanel jPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JTextArea contextArea = new JTextArea("Current context:\n" + context.stream()
                .collect(() -> new StringJoiner("\n"), (strJ, o) -> strJ.add(o.toString()), StringJoiner::merge)
                .toString());
        contextArea.setPreferredSize(new Dimension(300, 200));
        jPanel.add(contextArea);
        JPanel jPanel2 = new JPanel(new GridLayout(2, 1, 5, 5));
        JLabel jLabel = new JLabel("Please, write " + target);
        jPanel2.add(jLabel);
        JComboBox<String> jComboBox = new JComboBox<>(rulesStore.getPossibleParams().get(target).toArray(new String[0]));
        jPanel2.add(jComboBox);
        jPanel.add(jPanel2);
        jPanel.setPreferredSize(new Dimension(300, 300));
        JOptionPane.showMessageDialog(jFrame, jPanel, "Type your variant", JOptionPane.INFORMATION_MESSAGE);
        return jComboBox.getItemAt(jComboBox.getSelectedIndex());
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(jFrame, message, "Error: ", JOptionPane.ERROR_MESSAGE);
    }
}
