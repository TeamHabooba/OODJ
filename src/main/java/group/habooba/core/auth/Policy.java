package group.habooba.core.auth;

import group.habooba.core.base.AttributeMap;

import java.util.EnumSet;
import java.util.List;

public class Policy {

    private String name;
    private Effect effect;
    private Integer explicitPriority;
    private AttributeMap resourceMatcher;
    private AttributeMap subjectMatcher;
    private EnumSet<Action> actions;
    private List<Condition> conditions;
    private AttributeMap metadata;


    Policy() {
    }


    Policy(String name, Effect effect, Integer explicitPriority,
           AttributeMap resourceMatcher, AttributeMap subjectMatcher,
           EnumSet<Action> actions, List<Condition> conditions,
           AttributeMap metadata) {
        this.name = name;
        this.effect = effect;
        this.explicitPriority = explicitPriority;
        this.resourceMatcher = resourceMatcher;
        this.subjectMatcher = subjectMatcher;
        this.actions = actions;
        this.conditions = conditions;
        this.metadata = metadata;
    }


    String name() {
        return name;
    }

    void name(String value) {
        this.name = value;
    }

    Effect effect() {
        return effect;
    }

    void effect(Effect value) {
        this.effect = value;
    }

    Integer explicitPriority() {
        return explicitPriority;
    }

    void explicitPriority(Integer value) {
        this.explicitPriority = value;
    }

    AttributeMap resourceMatcher() {
        return resourceMatcher;
    }

    void resourceMatcher(AttributeMap value) {
        this.resourceMatcher = value;
    }

    AttributeMap subjectMatcher() {
        return subjectMatcher;
    }

    void subjectMatcher(AttributeMap value) {
        this.subjectMatcher = value;
    }

    EnumSet<Action> actions() {
        return actions;
    }

    void actions(EnumSet<Action> value) {
        this.actions = value;
    }

    List<Condition> conditions() {
        return conditions;
    }

    void conditions(List<Condition> value) {
        this.conditions = value;
    }

    AttributeMap metadata() {
        return metadata;
    }

    void metadata(AttributeMap value) {
        this.metadata = value;
    }
}