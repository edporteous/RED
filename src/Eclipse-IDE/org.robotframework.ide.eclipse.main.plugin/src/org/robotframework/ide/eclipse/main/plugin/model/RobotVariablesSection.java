/*
 * Copyright 2015 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.robotframework.ide.eclipse.main.plugin.model;

import java.util.List;
import java.util.Map.Entry;

import org.rf.ide.core.testdata.model.table.VariableTable;
import org.rf.ide.core.testdata.model.table.variables.AVariable;
import org.rf.ide.core.testdata.model.table.variables.AVariable.VariableType;

import com.google.common.collect.Lists;

public class RobotVariablesSection extends RobotSuiteFileSection {

    public static final String SECTION_NAME = "Variables";

    RobotVariablesSection(final RobotSuiteFile parent, final VariableTable variableTable) {
        super(parent, SECTION_NAME, variableTable);
    }

    @Override
    public void link() {
        for (final AVariable variableHolder : getLinkedElement().getVariables()) {
            final RobotVariable variable = new RobotVariable(this, variableHolder);
            elements.add(variable);
        }
    }

    @Override
    public VariableTable getLinkedElement() {
        return (VariableTable) super.getLinkedElement();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RobotVariable> getChildren() {
        return (List<RobotVariable>) super.getChildren();
    }

    public RobotVariable createVariable(final VariableType variableType, final String name) {
        return createVariable(getChildren().size(), variableType, name);
    }

    public RobotVariable createVariable(final int index, final VariableType variableType, final String name) {
        AVariable var;
        if (variableType == VariableType.SCALAR) {
            var = getLinkedElement().createScalarVariable(index, name, Lists.<String> newArrayList());
        } else if (variableType == VariableType.LIST) {
            var = getLinkedElement().createListVariable(index, name, Lists.<String> newArrayList());
        } else if (variableType == VariableType.DICTIONARY) {
            var = getLinkedElement().createDictionaryVariable(index, name,
                    Lists.<Entry<String, String>> newArrayList());
        } else {
            throw new IllegalArgumentException("Unable to create variable of type " + variableType.name());
        }

        final RobotVariable robotVariable = new RobotVariable(this, var);
        elements.add(index, robotVariable);
        return robotVariable;
    }

    public void addVariable(final RobotVariable variable) {
        addVariable(elements.size(), variable);
    }

    public void addVariable(final int index, final RobotVariable variable) {
        variable.setParent(this);
        elements.add(index, variable);
        getLinkedElement().addVariable(index, variable.getLinkedElement());
    }
}
