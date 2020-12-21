package memos.utils;

import memos.config.Config;

public enum StopwatchVisibility {
    NEVER("config.memos.stopwatchVisibility.never"),
    TEN_SEC("config.memos.stopwatchVisibility.ten_sec"),
    THIRTY_SEC("config.memos.stopwatchVisibility.thirty_sec"),
    ONE_MIN("config.memos.stopwatchVisibility.one_min"),
    FIVE_MIN("config.memos.stopwatchVisibility.five_min"),
    TEN_MIN("config.memos.stopwatchVisibility.ten_min"),
    ALWAYS ("config.memos.stopwatchVisibility.always");


    String key;

    private StopwatchVisibility(String key){
        this.key = key;
    }

    public String getName(){
        return this.key;
    }


    public boolean isVisible(){
        switch (Config.stopwatchVisibility){
            case ALWAYS:
                return true;
            case TEN_MIN:
                return Stopwatch.getTimeLeft() < 600;
            case FIVE_MIN:
                return Stopwatch.getTimeLeft() < 300;
            case ONE_MIN:
                return Stopwatch.getTimeLeft() < 60;
            case THIRTY_SEC:
                return Stopwatch.getTimeLeft() < 30;
            case TEN_SEC:
                return Stopwatch.getTimeLeft() < 10;
            default:
                return false;
        }
    }
}
