package SuperFreedomSurvive.Climate;

public enum Season {

    // 春季
    SPRING( 0 ),

    // 夏季
    SUMMER( 1 ),

    // 秋季
    FALL( 2 ),

    // 冬季
    WINTER( 3 );

    private final int value;

    Season(final int value) {
        this.value = value;
    }

    // 取得名稱
    public String getName() {
        switch (value) {
            case 0:
                return "春季";
            case 1:
                return "夏季";
            case 2:
                return "秋季";
            case 3:
                return "冬季";
            default:
                return null;
        }
    }


}

