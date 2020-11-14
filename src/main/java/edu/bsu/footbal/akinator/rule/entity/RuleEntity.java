package edu.bsu.footbal.akinator.rule.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RuleEntity {
    private final String attribute;
    private final String value;
}
