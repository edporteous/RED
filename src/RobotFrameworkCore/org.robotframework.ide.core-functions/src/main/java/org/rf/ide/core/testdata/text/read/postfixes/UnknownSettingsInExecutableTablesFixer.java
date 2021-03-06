/*
 * Copyright 2016 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.rf.ide.core.testdata.text.read.postfixes;

import java.util.List;

import org.rf.ide.core.testdata.model.RobotFile;
import org.rf.ide.core.testdata.model.RobotFileOutput;
import org.rf.ide.core.testdata.model.table.KeywordTable;
import org.rf.ide.core.testdata.model.table.RobotExecutableRow;
import org.rf.ide.core.testdata.model.table.TestCaseTable;
import org.rf.ide.core.testdata.model.table.keywords.KeywordUnknownSettings;
import org.rf.ide.core.testdata.model.table.keywords.UserKeyword;
import org.rf.ide.core.testdata.model.table.testcases.TestCase;
import org.rf.ide.core.testdata.model.table.testcases.TestCaseUnknownSettings;
import org.rf.ide.core.testdata.text.read.IRobotTokenType;
import org.rf.ide.core.testdata.text.read.recognizer.RobotToken;
import org.rf.ide.core.testdata.text.read.recognizer.RobotTokenType;

/**
 * @author wypych
 */
public class UnknownSettingsInExecutableTablesFixer implements IPostProcessFixAction {

    @Override
    public void applyFix(final RobotFileOutput parsingOutput) {
        final RobotFile fileModel = parsingOutput.getFileModel();
        final TestCaseTable testCaseTable = fileModel.getTestCaseTable();
        final KeywordTable keywordTable = fileModel.getKeywordTable();

        if (testCaseTable.isPresent()) {
            fixInTestCaseTable(testCaseTable);
        }

        if (keywordTable.isPresent()) {
            fixInUserKeywordTable(keywordTable);
        }
    }

    private void fixInUserKeywordTable(final KeywordTable keywordTable) {
        List<UserKeyword> keywords = keywordTable.getKeywords();
        for (final UserKeyword userKeyword : keywords) {
            for (int i = 0; i < userKeyword.getExecutionContext().size(); i++) {
                RobotExecutableRow<UserKeyword> robotExecutableRow = userKeyword.getExecutionContext().get(i);
                String raw = robotExecutableRow.getAction().getRaw().trim();
                if (raw.startsWith("[") && raw.endsWith("]")) {
                    userKeyword.removeExecutableLineWithIndex(i);
                    i--;

                    KeywordUnknownSettings keywordUnknownSettings = new KeywordUnknownSettings(
                            robotExecutableRow.getDeclaration());
                    List<IRobotTokenType> types = robotExecutableRow.getDeclaration().getTypes();
                    types.clear();
                    types.add(RobotTokenType.KEYWORD_SETTING_UNKNOWN_DECLARATION);
                    for (final RobotToken argument : robotExecutableRow.getArguments()) {
                        List<IRobotTokenType> argTypes = argument.getTypes();
                        argTypes.clear();
                        argTypes.add(RobotTokenType.KEYWORD_SETTING_UNKNOWN_ARGUMENTS);
                        keywordUnknownSettings.addArgument(argument);
                    }
                    for (final RobotToken commentPart : robotExecutableRow.getComment()) {
                        keywordUnknownSettings.addCommentPart(commentPart);
                    }
                    userKeyword.addUnknownSettings(keywordUnknownSettings);
                }
            }
        }
    }

    private void fixInTestCaseTable(final TestCaseTable testCaseTable) {
        List<TestCase> testCases = testCaseTable.getTestCases();
        for (final TestCase testCase : testCases) {
            for (int i = 0; i < testCase.getExecutionContext().size(); i++) {
                RobotExecutableRow<TestCase> robotExecutableRow = testCase.getExecutionContext().get(i);
                String raw = robotExecutableRow.getAction().getRaw().trim();
                if (raw.startsWith("[") && raw.endsWith("]")) {
                    testCase.removeExecutableLineWithIndex(i);
                    i--;

                    TestCaseUnknownSettings testCaseUnknownSettings = new TestCaseUnknownSettings(
                            robotExecutableRow.getDeclaration());
                    List<IRobotTokenType> types = robotExecutableRow.getDeclaration().getTypes();
                    types.clear();
                    types.add(RobotTokenType.TEST_CASE_SETTING_UNKNOWN_DECLARATION);
                    for (final RobotToken argument : robotExecutableRow.getArguments()) {
                        List<IRobotTokenType> argTypes = argument.getTypes();
                        argTypes.clear();
                        argTypes.add(RobotTokenType.TEST_CASE_SETTING_UNKNOWN_ARGUMENTS);
                        testCaseUnknownSettings.addArgument(argument);
                    }
                    for (final RobotToken commentPart : robotExecutableRow.getComment()) {
                        testCaseUnknownSettings.addCommentPart(commentPart);
                    }
                    testCase.addUnknownSettings(testCaseUnknownSettings);
                }
            }
        }
    }
}
