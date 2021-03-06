
public class NBody{
	public static double readRadius (String fileName){
		In in = new In(fileName);
		int numberofPlanets = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet [] readPlanets (String fileName){
		In in = new In(fileName);
		int numberofPlanets = in.readInt();
		double radius = in.readDouble();
		Planet [] pArray = new Planet [3];

	}
}