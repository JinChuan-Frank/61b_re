public class Draw{
  public static String imageToDraw = "images/starfield.jpg";
  public static void drawBackground() {
	StdDraw.setScale(-2.50e+11, 2.50e+11);
	StdDraw.clear();

		StdDraw.picture(0, 0, imageToDraw);
	}
  public static void main(String [] args){
      drawBackground();
  }
}
