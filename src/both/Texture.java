package both;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class Texture {

    // setters
    public static void setTexture(FXGraphics2D graphics2D, HashMap<Rectangle2D, Color> texture, double x, double y) {

        x += Config.PIXEL_OFFSET_X;
        y += Config.PIXEL_OFFSET_Y;

        for (Map.Entry<Rectangle2D, Color> entry : texture.entrySet()) {

            Rectangle2D rect = entry.getKey();

            graphics2D.setColor(entry.getValue());
            graphics2D.fillRect((int) (x + rect.getX()), (int) (y + rect.getY()), (int) rect.getWidth(), (int) rect.getHeight());
        }
    }

    // getters
    public static HashMap<Rectangle2D, Color> getTexture(double w, double h, Color color) {

        return getTexture(w, h, darkenColor(color, 2), color);
    }

    public static HashMap<Rectangle2D, Color> getTexture(double w, double h, Color color1, Color color2) {

        return getTexture(0, 0, w, h, color1, color2);
    }

    public static HashMap<Rectangle2D, Color> getTexture(int x, int y, double w, double h, Color color1, Color color2) {

        HashMap<Rectangle2D, Color> texture = new HashMap<>();

        double scale = Config.PIXEL_SIZE;

        for (int a = 0; a < (w / scale); a++)
            for (int b = 0; b < (h / scale); b++)
                texture.put(new Rectangle2D.Double(x + (a * scale), y + (b * scale), scale, scale), randomColor(color1, color2));

        return texture;
    }

    public static HashMap<Rectangle2D, Color> getPinTexture(Color color) {

        HashMap<Rectangle2D, Color> texture = new HashMap<>();

        double scale = Config.PIXEL_SIZE;
        double size = (Config.BOARD_PIN_SIZE / scale);
        double center = (Config.BOARD_PIN_CENTER_SIZE / scale);

        double x = getPinOffset();
        double y = getPinOffset();

        for (int a = 0; a < size; a++)
            for (int b = 0; b < size; b++)
                texture.put(new Rectangle2D.Double((int) (x + (a * scale)), (int) (y + (b * scale)), scale, scale), randomColor(darkenColor(color, 2), darkenColor(color, 1)));

        x += getPinCenterOffset();
        y += getPinCenterOffset();

        for (int a = 0; a < center; a++)
            for (int b = 0; b < center; b++)
                texture.put(new Rectangle2D.Double((int) (x + (a * scale)), (int) (y + (b * scale)), scale, scale), randomColor(darkenColor(color, 1), color));

        return texture;
    }

    // methods
    private static Color randomColor(Color color1, Color color2) {

        int r = color1.getRed()   - (int) (Math.random() * (color1.getRed()   - color2.getRed()));
        int b = color1.getBlue()  - (int) (Math.random() * (color1.getBlue()  - color2.getBlue()));
        int g = color1.getGreen() - (int) (Math.random() * (color1.getGreen() - color2.getGreen()));

        return new Color(r, g, b);
    }

    private static Color darkenColor(Color color, int darken) {

        Color darker = new Color(color.getRed(), color.getGreen(), color.getBlue());

        for (int i = 0; i < darken; i++)
            darker = darker.darker();

        return darker;
    }

    // getters
    private static double getPinOffset() {

        return (Config.BOARD_SQUARE_SIZE - Config.BOARD_PIN_SIZE) / 2;
    }

    private static double getPinCenterOffset() {

        return (Config.BOARD_PIN_SIZE - Config.BOARD_PIN_CENTER_SIZE) / 2;
    }
}
