package byog.lab6;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;


public class TestStdDraw {
    public static void main(String[] args) {
        Font font = new Font("Arial", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.5, "Hello, World");;
    }
}
