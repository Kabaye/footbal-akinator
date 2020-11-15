package edu.bsu.footbal.akinator.app.processor;

import edu.bsu.footbal.akinator.app.processor.controller.GameProcessController;
import edu.bsu.footbal.akinator.app.shutdown.ShutdownManager;
import edu.bsu.footbal.akinator.app.view.ViewController;
import edu.bsu.footbal.akinator.rule.store.RulesStore;
import edu.bsu.footbal.akinator.utils.FunctionsMap;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * created by @Kabaye
 * date 14.11.2020
 */

@Service
@RequiredArgsConstructor
public class AppProcessor {
    private final ViewController viewController;
    private final RulesStore rulesStore;
    private final ShutdownManager shutdownManager;
    private final GameProcessController gameProcessController;

    private final FunctionsMap<Integer, Runnable> mainMenuFunctions = new FunctionsMap<>();

    @PostConstruct
    public void startProcess() {
        mainMenuFunctions.put(0, this::showRules)
                .put(1, this::startGame)
                .put(2, this::shutdown)
                .put(-1, this::shutdown);
        loopGame();
    }

    private void loopGame() {
        while (true) {
            int i = viewController.showMainMenu();
            try {
                mainMenuFunctions.get(i).run();
            } catch (Exception e) {
                viewController.showErrorMessage(e.getMessage());
            }
        }
    }

    public void startGame() {
        String clubName = gameProcessController.startNewGame();
        viewController.showResult(clubName);
    }

    public void showRules() {
        viewController.showAllRules();
    }

    public void shutdown() {
        shutdownManager.initializeShutdown(0);
    }
}
