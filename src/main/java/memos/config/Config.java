package memos.config;

import memos.Memories;
import memos.utils.MemosScreenshotUtils;
import memos.utils.StopwatchVisibility;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Properties;

public class Config {

    private static final String openingLine = "# " + Memories.MOD_NAME + " | ";

    private final static String path = (Paths.get("").toAbsolutePath().toString() + "/config/" + Memories.MOD_ID + ".properties");

    private static File configFile = new File(path);

    public static boolean catalogScreenshots;
    public static String fileName;
    public static boolean chatMessagesEnabled;
    public static String chatMessage;
    public static boolean borderEnabled;
    public static boolean stopwatchEnabled;
    public static float stopwatchInterval;
    public static StopwatchVisibility stopwatchVisibility;
    public static float stopwatchX;
    public static float stopwatchY;
    public static int stopwatchColor;

    public static void saveConfig() {
        try {

            FileOutputStream fos = new FileOutputStream(configFile, false);

            fos.write((openingLine + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)) + "\n").getBytes());
            fos.write(("\n").getBytes());
            fos.write(("catalogScreenshots=" + catalogScreenshots + "\n").getBytes());
            fos.write(("fileName=" + fileName + "\n").getBytes());
            fos.write(("chatMessagesEnabled=" + chatMessagesEnabled + "\n").getBytes());
            fos.write(("chatMessage=" + chatMessage + "\n").getBytes());
            fos.write(("borderEnabled=" + borderEnabled + "\n").getBytes());
            fos.write(("stopwatchEnabled=" + stopwatchEnabled + "\n").getBytes());
            fos.write(("stopwatchInterval=" + stopwatchInterval + "\n").getBytes());
            fos.write(("stopwatchVisibility=" + stopwatchVisibility + "\n").getBytes());
            fos.write(("stopwatchX=" + stopwatchX + "\n").getBytes());
            fos.write(("stopwatchY=" + stopwatchY + "\n").getBytes());
            fos.write(("stopwatchColorA=" + ((stopwatchColor >> 24) & 255)+"\n").getBytes());
            fos.write(("stopwatchColorR=" + ((stopwatchColor >> 16) & 255)+"\n").getBytes());
            fos.write(("stopwatchColorG=" + ((stopwatchColor >> 8) & 255)+"\n").getBytes());
            fos.write(("stopwatchColorB="  + ((stopwatchColor) & 255)+"\n").getBytes());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadConfig() {
        try {
            if (!configFile.exists() || !configFile.canRead()){
                initWithDefault();
                saveConfig();
            }

            Properties properties = new Properties();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)))) {
                String line = br.readLine();
                if (!line.startsWith(openingLine)) {
                    initWithDefault();
                    saveConfig();
                }
                properties.load(br);

                try {
                    catalogScreenshots = ((String) properties.computeIfAbsent("catalogScreenshots", a -> "true")).equalsIgnoreCase("true");
                    fileName = ((String) properties.computeIfAbsent("fileName", a -> "yyyy-MM-dd_HH.mm.ss"));
                    chatMessagesEnabled = ((String) properties.computeIfAbsent("chatMessagesEnabled", a -> "true")).equalsIgnoreCase("true");
                    chatMessage = ((String) properties.computeIfAbsent("chatMessage", a -> ""));
                    borderEnabled = ((String) properties.computeIfAbsent("borderEnabled", a -> "false")).equalsIgnoreCase("true");
                    stopwatchEnabled = ((String) properties.computeIfAbsent("stopwatchEnabled", a -> "true")).equalsIgnoreCase("true");
                    stopwatchInterval = Float.parseFloat((String) properties.computeIfAbsent("stopwatchInterval", o -> "60"));
                    stopwatchVisibility = StopwatchVisibility.valueOf((String)properties.computeIfAbsent("stopwatchVisibility", a -> StopwatchVisibility.NEVER));
                    stopwatchX = Float.parseFloat((String) properties.computeIfAbsent("stopwatchX", o -> "1"));
                    stopwatchY = Float.parseFloat((String) properties.computeIfAbsent("stopwatchY", o -> "1"));
                    {
                        int a, r, g, b;
                        a = Integer.parseInt((String) properties.computeIfAbsent("stopwatchColorA", o -> "255"));
                        r = Integer.parseInt((String) properties.computeIfAbsent("stopwatchColorR", o -> "255"));
                        g = Integer.parseInt((String) properties.computeIfAbsent("stopwatchColorG", o -> "255"));
                        b = Integer.parseInt((String) properties.computeIfAbsent("stopwatchColorB", o -> "255"));
                        stopwatchColor = (a << 24) + (r << 16) + (g << 8) + b;
                    }

                    if(!MemosScreenshotUtils.validateFileName(fileName)){
                        fileName = "yyyy-MM-dd_HH.mm.ss";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    initWithDefault();
                    saveConfig();
                }
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void initWithDefault(){
        catalogScreenshots = true;
        fileName = "yyyy-MM-dd_HH.mm.ss";
        chatMessagesEnabled = true;
        chatMessage = "";
        borderEnabled = false;
        stopwatchEnabled = true;
        stopwatchInterval = 60;
        stopwatchVisibility = StopwatchVisibility.NEVER;
        stopwatchX = 1;
        stopwatchY = 1;
        stopwatchColor = 0xFFFFFFFF;
        }

    }
