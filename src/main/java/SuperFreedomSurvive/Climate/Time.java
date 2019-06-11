package SuperFreedomSurvive.Climate;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class Time {
    // 時間

    public static World world = Bukkit.getWorld("w");
    public static boolean weather = Function.getRandomWeather();


    // 取得時間 1 ~ 24000
    public static int getTime() {
        long fullTime = world.getFullTime();

        return (int)( fullTime % 24000 );
    }
    // 取得日 1 ~ 30
    public static int getDay() {
        long fullTime = world.getFullTime();
        long day = fullTime / 24000;

        return (int)( day % 30 + 1 );
    }
    // 取得月 1 ~ 12
    public static int getMonth() {
        long fullTime = world.getFullTime();
        long day = fullTime / 24000;
        long month = day / 30;

        return (int)( month % 12 + 1 );
    }
    // 取得年 1 ~~
    public static long getYear() {
        long fullTime = world.getFullTime();
        long day = fullTime / 24000;
        long month = day / 30;

        return month / 12;
    }
    // /time add 720000
    // 2160000
    // /time add 2160000

    // 取得季節
    public static Season getSeason() {
        return getCalendar().getSeason();
    }

    // 取得月曆
    public static Calendar getCalendar() {
        switch ( getMonth() ) {
            case 1:
                return Calendar.JANUARY;
            case 2:
                return Calendar.FEBRUARY;
            case 3:
                return Calendar.MARCH;
            case 4:
                return Calendar.APRIL;
            case 5:
                return Calendar.MAY;
            case 6:
                return Calendar.JUNE;
            case 7:
                return Calendar.JULY;
            case 8:
                return Calendar.AUGUST;
            case 9:
                return Calendar.SEPTEMBER;
            case 10:
                return Calendar.OCTOBER;
            case 11:
                return Calendar.NOVEMBER;
            case 12:
                return Calendar.DECEMBER;
            default:
                return null;
        }
    }

    // 取得天氣
    public static boolean getWeather() {
        if ( getCalendar().isAlwaysWeather() ) {
            return true;
        } else if ( getCalendar().isAlwaysNoWeather() ) {
            return false;
        }
        return weather;
    }

    // 設置天氣
    public static void setWeather(boolean b) {
        weather = b;
    }

}
