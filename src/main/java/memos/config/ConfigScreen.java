package memos.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import memos.Stopwatch;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

public class ConfigScreen {
    public static Screen getConfigScreen(Screen parent) {

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTransparentBackground(true)
                .setTitle(new TranslatableText("config.memos.title"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("config.memos.category.none"));


        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.memos.stopwatchEnabled"), Config.stopwatchEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(value -> Config.stopwatchEnabled = value)
                .setTooltip(new TranslatableText("config.memos.stopwatchEnabled.tooltip"))
                .build());

        general.addEntry(entryBuilder.startIntField(new TranslatableText("config.memos.interval"), Config.interval)
                .setDefaultValue(60)
                .setMin(1)
                .setSaveConsumer(value -> {
                    Config.interval = value;
                    Stopwatch.reset();
                })
                .setTooltip(new TranslatableText("config.memos.interval.tooltip"))
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.memos.modifiedFileName"), Config.modifiedFileName)
                .setDefaultValue(true)
                .setSaveConsumer(value -> Config.modifiedFileName = value)
                .setTooltip(new TranslatableText("config.memos.modifiedFileName.tooltip"))
                .build());


        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.memos.chatMessage"), Config.chatMessage)
                .setDefaultValue(false)
                .setSaveConsumer(value -> Config.chatMessage = value)
                .setTooltip(new TranslatableText("config.memos.chatMessage.tooltip"))
                .build());


        //should be done
        //todo lang
        return builder.setSavingRunnable(()->{
            try {
                Config.saveConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Config.loadConfig();
        }).build();
    }
}
