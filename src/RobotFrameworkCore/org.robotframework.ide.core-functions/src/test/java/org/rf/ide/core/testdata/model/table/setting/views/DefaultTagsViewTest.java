/*
 * Copyright 2016 Nokia Solutions and Networks
 * Licensed under the Apache License, Version 2.0,
 * see license.txt file for details.
 */
package org.rf.ide.core.testdata.model.table.setting.views;

import static org.assertj.core.api.Assertions.assertThat;
import static org.rf.ide.core.testdata.model.table.setting.views.ModelTokenTestHelper.getText;

import org.junit.Test;
import org.rf.ide.core.testdata.model.RobotFile;
import org.rf.ide.core.testdata.model.table.SettingTable;
import org.rf.ide.core.testdata.model.table.setting.DefaultTags;
import org.rf.ide.core.testdata.text.read.recognizer.RobotToken;

import com.google.common.base.Optional;

public class DefaultTagsViewTest {

    @Test
    public void test_tags_retrival_twoDefaultTags() {
        // prepare
        final RobotFile robotFile = new RobotFile(null);
        robotFile.includeSettingTableSection();
        SettingTable settingTable = robotFile.getSettingTable();

        RobotToken tagDecOne = new RobotToken();
        DefaultTags defaultOne = new DefaultTags(tagDecOne);
        defaultOne.addTag("tag1");
        defaultOne.addTag("tag2");

        RobotToken tagDecTwo = new RobotToken();
        DefaultTags defaultTwo = new DefaultTags(tagDecTwo);
        defaultTwo.addTag("tag1a");
        defaultTwo.addTag("tag2a");

        settingTable.addDefaultTags(defaultOne);
        settingTable.addDefaultTags(defaultTwo);

        // execute
        Optional<DefaultTags> defaultTags = settingTable.defaultTags();

        // verify
        assertThat(defaultTags.isPresent()).isTrue();
        DefaultTags joined = defaultTags.get();
        assertThat(joined.getTags()).hasSize(4);
        assertThat(getText(joined.getTags())).containsExactly("tag1", "tag2", "tag1a", "tag2a");
        assertThat(settingTable.getDefaultTags()).hasSize(2);
    }

    @Test
    public void test_tags_retrival_afterTagRemove_singleDefaultTags() {
        // prepare
        final RobotFile robotFile = new RobotFile(null);
        robotFile.includeSettingTableSection();
        SettingTable settingTable = robotFile.getSettingTable();

        RobotToken tagDecOne = new RobotToken();
        DefaultTags defaultOne = new DefaultTags(tagDecOne);
        defaultOne.addTag("tag1");
        defaultOne.addTag("tag2");

        RobotToken tagDecTwo = new RobotToken();
        DefaultTags defaultTwo = new DefaultTags(tagDecTwo);
        defaultTwo.addTag("tag1a");
        defaultTwo.addTag("tag2a");

        settingTable.addDefaultTags(defaultOne);
        settingTable.addDefaultTags(defaultTwo);

        // execute
        Optional<DefaultTags> defaultTags = settingTable.defaultTags();
        defaultTags.get().addTag("tag3");

        // verify
        assertThat(defaultTags.isPresent()).isTrue();
        DefaultTags joined = defaultTags.get();
        assertThat(joined.getTags()).hasSize(5);
        assertThat(getText(joined.getTags())).containsExactly("tag1", "tag2", "tag1a", "tag2a", "tag3");
        assertThat(settingTable.getDefaultTags()).hasSize(1);
        assertThat(settingTable.getDefaultTags().get(0)).isSameAs(joined);
    }

    @Test
    public void test_tags_retrival_afterModification_twoDefaultTags() {
        // prepare
        final RobotFile robotFile = new RobotFile(null);
        robotFile.includeSettingTableSection();
        SettingTable settingTable = robotFile.getSettingTable();

        RobotToken tagDecOne = new RobotToken();
        DefaultTags defaultOne = new DefaultTags(tagDecOne);
        defaultOne.addTag("tag1");
        defaultOne.addTag("tag2");

        RobotToken tagDecTwo = new RobotToken();
        DefaultTags defaultTwo = new DefaultTags(tagDecTwo);
        defaultTwo.addTag("tag1a");
        defaultTwo.addTag("tag2a");

        settingTable.addDefaultTags(defaultOne);
        settingTable.addDefaultTags(defaultTwo);

        // execute
        Optional<DefaultTags> defaultTags = settingTable.defaultTags();
        defaultTwo.getTags().get(0).setText("mod");

        // verify
        assertThat(defaultTags.isPresent()).isTrue();
        DefaultTags joined = defaultTags.get();
        assertThat(joined.getTags()).hasSize(4);
        assertThat(getText(joined.getTags())).containsExactly("tag1", "tag2", "mod", "tag2a");
        assertThat(settingTable.getDefaultTags()).hasSize(2);
    }
}
