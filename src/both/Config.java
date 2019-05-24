package both;

public class Config {

    // connection
    public static final int     SERVER_PORT             = 42069;
//    public static final String  SERVER_HOST            = "bkne.jijbentzacht.nl";
    public static final String  SERVER_HOST             = "localhost";

    // game
    public static final int     GAME_MAX_PLAYERS        = 10;
    public static final int     GAME_FPS                = 20;
    public static final int     GAME_SCREEN_WIDTH       = 1000;
    public static final int     GAME_SCREEN_HEIGHT      = 500;
    public static final int     GAME_PIN_PER_CLIENT     = 4;
    public static final String  GAME_TITLE              = "BKnE - Battle Royale";

    // style
    public static final String  CSS_PATH                = "client/view/style/style.css";

    // board
    public static final int     BOARD_SQUARE_SIZE       = 50;
    public static final int     BOARD_BORDER_SIZE       = 10;
    public static final int     BOARD_PIN_SIZE          = 30;
    public static final int     BOARD_PIN_BORDER_SIZE   = 5;
}
