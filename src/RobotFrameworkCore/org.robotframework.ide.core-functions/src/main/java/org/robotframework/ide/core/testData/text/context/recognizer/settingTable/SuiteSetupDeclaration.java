package org.robotframework.ide.core.testData.text.context.recognizer.settingTable;

import org.robotframework.ide.core.testData.text.context.recognizer.ATableElementRecognizer;
import org.robotframework.ide.core.testData.text.lexer.RobotWordType;


/**
 * <pre>
 * *** Settings ***
 * Suite Setup
 * </pre>
 * 
 * @author wypych
 * @since JDK 1.7 update 74
 * @version Robot Framework 2.9 alpha 2
 * 
 */
public class SuiteSetupDeclaration extends ATableElementRecognizer {

    public SuiteSetupDeclaration() {
        super(SettingTableRobotContextType.TABLE_SETTINGS_SUITE_SETUP,
                createExpectedWithOptionalColonAsLast(RobotWordType.SUITE_WORD,
                        RobotWordType.SETUP_WORD));
    }
}
