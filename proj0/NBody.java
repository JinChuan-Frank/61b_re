
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
		Planet [] pArray = new Planet [numberofPlanets];
		for (int i = 0; i < numberofPlanets; i += 1 ){
			//pArray[i] = new Planet(xP, yP, xV, yV, m, img);

			double xP = in.readDouble();
			double yP	= in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			pArray[i] = new Planet (xP, yP, xV, yV, m, img);
		}
		return pArray;
	}
}
