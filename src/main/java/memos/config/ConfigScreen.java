package memos.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import memos.utils.MemosScreenshotUtils;
import memos.utils.Stopwatch;
import memos.utils.StopwatchVisibility;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

public class ConfigScreen {
    public static Screen getConfigScreen(Screen parent) {

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTransparentBackground(true)
                .setTitle(new TranslatableText("config.memos.title"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory generalCategory = builder.getOrCreateCategory(new TranslatableText("config.memos.category.general"));
        ConfigCategory stopwatchCategory = builder.getOrCreateCategory(new TranslatableText("config.memos.category.stopwatch"));

        generalCategory.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.memos.catalogScreenshots"), Config.catalogScreenshots)
                .setDefaultValue(true)
                .setSaveConsumer(value -> Config.catalogScreenshots = value)
                .setTooltip(new TranslatableText("config.memos.catalogScreenshots.tooltip"))
                .build());


        generalCategory.addEntry(entryBuilder.startStringDropdownMenu(new TranslatableText("config.memos.fileName"), Config.fileName)
                .setDefaultValue("yyyy-MM-dd_HH.mm.ss")
                .setSelections(MemosScreenshotUtils.getSuggestionList())
                .setSaveConsumer(value ->{
                    if(MemosScreenshotUtils.validateFileName(value)){
                        Config.fileName = value;
                    }
                })
                .build());


        generalCategory.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.memos.chatMessagesEnabled"), Config.chatMessagesEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(value -> Config.chatMessagesEnabled = value)
                .build());

        generalCategory.addEntry(entryBuilder.startStringDropdownMenu(new TranslatableText("config.memos.chatMessage"), Config.chatMessage)
                .setDefaultValue("")
                .setSaveConsumer(value -> Config.chatMessage = value)
                .setTooltip(new TranslatableText("config.memos.chatMessage.tooltip"))
                .build());

        generalCategory.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.memos.borderEnabled"), Config.borderEnabled)
                .setDefaultValue(false)
                .setSaveConsumer(value -> Config.borderEnabled = value)
                .build());

        stopwatchCategory.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.memos.stopwatchEnabled"), Config.stopwatchEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(value -> Config.stopwatchEnabled = value)
                .build());

        stopwatchCategory.addEntry(entryBuilder.startFloatField(new TranslatableText("config.memos.stopwatchInterval"), Config.stopwatchInterval)
                .setDefaultValue(60)
                .setMin(0.16f)
                .setSaveConsumer(value -> {
                    Config.stopwatchInterval = value;
                    Stopwatch.reset();
                })
                .setTooltip(new TranslatableText("config.memos.stopwatchInterval.tooltip"))
                .build());


        stopwatchCategory.addEntry(entryBuilder.startSelector(new TranslatableText("config.memos.stopwatchVisibility"),StopwatchVisibility.values(), Config.stopwatchVisibility)
                .setDefaultValue(StopwatchVisibility.NEVER)
                .setNameProvider(value -> new TranslatableText(value.getName()))
                .setSaveConsumer(value -> Config.stopwatchVisibility = value)
                .setTooltip(new TranslatableText("config.memos.stopwatchVisibility.tooltip"))
                .build());


        stopwatchCategory.addEntry(entryBuilder.startFloatField(new TranslatableText("config.memos.stopwatchX"), Config.stopwatchX)
                .setDefaultValue(1)
                .setMin(0)
                .setMax(100)
                .setSaveConsumer(value -> {
                    Config.stopwatchX = value;
                })
                .setTooltip(new TranslatableText("config.memos.stopwatchX.tooltip"))
                .build());

        stopwatchCategory.addEntry(entryBuilder.startFloatField(new TranslatableText("config.memos.stopwatchY"), Config.stopwatchY)
                .setDefaultValue(1)
                .setMin(0)
                .setMax(100)
                .setSaveConsumer(value -> {
                    Config.stopwatchY = value;
                })
                .setTooltip(new TranslatableText("config.memos.stopwatchY.tooltip"))
                .build());

        stopwatchCategory.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("config.memos.stopwatchColor"), Config.stopwatchColor)
                .setDefaultValue(0xFFFFFFFF)
                .setSaveConsumer(value -> Config.stopwatchColor = value)
        .build());

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
