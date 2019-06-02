package both;

public class Config {

    // connection
    public static final int     SERVER_PORT                     = 42069;
//    public static final String  SERVER_HOST                   = "bkne.jijbentzacht.nl";
    public static final String  SERVER_HOST                     = "localhost";

    // game
    public static final int     GAME_MAX_PLAYERS                = 2;
    public static final int     GAME_SCREEN_WIDTH               = 1000;
    public static final int     GAME_SCREEN_HEIGHT              = 500;
    public static final int     GAME_PIN_PER_CLIENT             = 4;
    public static final String  GAME_TITLE                      = "BKnE";

    // lobby
    public static final int     LOBBY_WAIT_TIME                 = 60;

    // style
    public static final String  CSS_PATH                        = "client/view/style/style.css";
    public static final int     PIXEL_SIZE                      = 5;

    // board
    public static final double  BOARD_SQUARE_SIZE               = 40;
    public static final double  BOARD_BORDER_SIZE               = 10;
    public static final double  BOARD_PIN_SIZE                  = 30;
    public static final double  BOARD_PIN_CENTER_SIZE           = 20;
}
