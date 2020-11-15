package edu.bsu.footbal.akinator.rule.configuration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.bsu.footbal.akinator.rule.entity.Rule;
import edu.bsu.footbal.akinator.rule.store.RulesStore;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Collections;
import java.util.Set;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * created by @Kabaye
 * date 14.11.2020
 */

@Configuration
public class RuleConfiguration {
    @Bean
    @SneakyThrows
    @SuppressWarnings("all")
    public RulesStore rulesStore() {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("rules.json");
        Set<Rule> rules = new Gson().fromJson(new FileReader(new File(resource.toURI())), new TypeToken<Set<Rule>>() {
        }.getType());
        return new RulesStore(Collections.unmodifiableSet(rules));
    }
}
