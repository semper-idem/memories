package memos.config;

import memos.Memories;

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

    public static boolean stopwatchEnabled;
    public static int interval;
    public static boolean modifiedFileName;
    public static boolean chatMessage;

    public static void saveConfig() {
        try {

            FileOutputStream fos = new FileOutputStream(configFile, false);

            fos.write((openingLine + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)) + "\n").getBytes());
            fos.write(("\n").getBytes());

            fos.write(("stopwatchEnabled=" + stopwatchEnabled + "\n").getBytes());
            fos.write(("interval=" + interval + "\n").getBytes());
            fos.write(("modifiedFileName=" + modifiedFileName + "\n").getBytes());
            fos.write(("chatMessage=" + chatMessage + "\n").getBytes());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadConfig() {
        try {
            if (!configFile.exists() || !configFile.canRead() || configFile.length() < 8){
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
                    stopwatchEnabled = ((String) properties.computeIfAbsent("stopwatchEnabled", a -> "true")).equalsIgnoreCase("true");
                    interval = Integer.parseInt((String) properties.computeIfAbsent("interval", o -> "60"));
                    modifiedFileName = ((String) properties.computeIfAbsent("modifiedFileName", a -> "true")).equalsIgnoreCase("true");
                    chatMessage = ((String) properties.computeIfAbsent("chatMessage", a -> "false")).equalsIgnoreCase("true");

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
        stopwatchEnabled = true;
        interval = 60;
        modifiedFileName = true;
        chatMessage = false;
        }

    }
