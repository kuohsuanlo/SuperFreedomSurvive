package SuperFreedomSurvive.Climate;

public enum Calendar {

    // 1月
    JANUARY( 1 ),

    // 2月
    FEBRUARY( 2 ),

    // 3月
    MARCH( 3 ),

    // 4月
    APRIL( 4 ),

    // 5月
    MAY( 5 ),

    // 6月
    JUNE( 6 ),

    // 7月
    JULY( 7 ),

    // 8月
    AUGUST( 8 ),

    // 9月
    SEPTEMBER( 9 ),

    // 10月
    OCTOBER( 10 ),

    // 11月
    NOVEMBER( 11 ),

    // 12月
    DECEMBER( 12 );


    private final int value;

    Calendar(final int value) {
        this.value = value;
    }

    // 是否總是下雨
    public boolean isAlwaysWeather() {
        switch (value) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return false;
            case 8:
                return true;
            case 9:
            case 10:
            case 11:
                return false;
            case 12:
                return true;
            default:
                return false;
        }
    }


    // 是否永不下雨
    public boolean isAlwaysNoWeather() {
        switch (value) {
            case 1:
            case 2:
            case 3:
            case 4:
                return false;
            case 5:
            case 6:
            case 7:
                return true;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return false;
            default:
                return false;
        }
    }


    // 是否可打雷
    public boolean hasThunder() {
        switch (value) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return false;
            case 8:
            case 9:
            case 10:
            case 11:
                return true;
            case 12:
                return false;
            default:
                return false;
        }
    }


    // 取得季節
    public Season getSeason() {
        switch (value) {
            case 2:
            case 3:
            case 4:
                return Season.SPRING;
            case 5:
            case 6:
            case 7:
                return Season.SUMMER;
            case 8:
            case 9:
            case 10:
                return Season.FALL;
            case 11:
            case 12:
            case 1:
                return Season.WINTER;
            default:
                return null;
        }
    }

    // 取得名稱
    public String getName() {
        switch (value) {
            case 1:
                return "1月";
            case 2:
                return "2月";
            case 3:
                return "3月";
            case 4:
                return "4月";
            case 5:
                return "5月";
            case 6:
                return "6月";
            case 7:
                return "7月";
            case 8:
                return "8月";
            case 9:
                return "9月";
            case 10:
                return "10月";
            case 11:
                return "11月";
            case 12:
                return "12月";
            default:
                return null;
        }
    }
}
