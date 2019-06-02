package both;

import java.awt.*;

public class Config {

    // connection
    public static final int     SERVER_PORT                     = 42069;
    //public static final String  SERVER_HOST                   = "bkne.jijbentzacht.nl";
    public static final String  SERVER_HOST                     = "localhost";

    // game
    public static final int     GAME_MAX_PLAYERS                = 3;
    public static final int     GAME_SCREEN_WIDTH               = 1000;
    public static final int     GAME_SCREEN_HEIGHT              = 500;
    public static final int     GAME_PIN_PER_CLIENT             = 4;
    public static final String  GAME_TITLE                      = "BKnE";

    // lobby
    public static final int     LOBBY_WAIT_TIME                 = 60;

    // style
    public static final String  CSS_PATH                        = "client/view/style/style.css";

    // texture
    public static final double  PIXEL_SIZE                      = 5;

    // board
    public static final Color   BOARD_COLOR                     = Color.getHSBColor(.03f, .65f, .2f);
    public static final Color   BOARD_SQUARE_COLOR_1            = Color.getHSBColor(.11f, .4f, .9f);
    public static final Color   BOARD_SQUARE_COLOR_2            = Color.getHSBColor(.09f, .2f, .7f);
    public static final double  BOARD_SQUARE_SIZE               = 40;
    public static final double  BOARD_BORDER_SIZE               = 10;
    public static final double  BOARD_PIN_SIZE                  = 30;
    public static final double  BOARD_PIN_CENTER_SIZE           = 20;
}
