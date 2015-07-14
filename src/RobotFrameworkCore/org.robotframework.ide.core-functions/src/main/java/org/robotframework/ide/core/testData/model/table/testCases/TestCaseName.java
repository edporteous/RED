package org.robotframework.ide.core.testData.model.table.testCases;

import org.robotframework.ide.core.testData.model.AModelElement;
import org.robotframework.ide.core.testData.model.LineElement;
import org.robotframework.ide.core.testData.model.LineElement.ElementType;
import org.robotframework.ide.core.testData.model.RobotLine;


public class TestCaseName extends AModelElement {

    public TestCaseName(RobotLine containingLine, LineElement originalElement) {
        super(ElementType.TEST_CASE_NAME, containingLine, originalElement);
    }
}
