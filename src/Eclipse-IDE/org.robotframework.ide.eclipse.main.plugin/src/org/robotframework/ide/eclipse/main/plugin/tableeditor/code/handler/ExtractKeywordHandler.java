package org.robotframework.ide.eclipse.main.plugin.tableeditor.code.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.tools.compat.parts.DIHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.robotframework.ide.eclipse.main.plugin.tableeditor.code.handler.ExtractKeywordHandler.E4ExtractKeywordHandler;
import org.robotframework.viewers.Selections;

public class ExtractKeywordHandler extends DIHandler<E4ExtractKeywordHandler> {

    public ExtractKeywordHandler() {
        super(E4ExtractKeywordHandler.class);
    }

    public static class E4ExtractKeywordHandler {

        @Execute
        public Object extractKeyword(@Named(Selections.SELECTION) final IStructuredSelection selection) {
            throw new RuntimeException("Not yet implemented!");
        }
    }
}
