
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

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Planet [] pArray = readPlanets(filename);
		double r = readRadius(filename);
		StdDraw.setScale(-r, r);
		String backgroud = "images/starfield.jpg";
    StdDraw.picture(0, 0, backgroud);
		for (Planet p : pArray){
			p.draw();
		}
		//StdDraw.enableDoubleBuffering();
		//create a new time variable for looping
		double t1 = 0;
		while (t1 <= T){
			for (int i =0; i < pArray.length; i+=1 ){
				double [] xForces = new double [pArray.length];
				double [] yForces = new double [pArray.length];
				double xForce = pArray[i].calcNetForceExertedByX(pArray);
				double yforce = pArray[i].calcNetForceExertedByY(pArray);
				xForces[i] = xForce;
				yForces[i] = yforce;
				pArray[i].update(xForce, yforce, dt);
				StdDraw.picture(0, 0, backgroud);
			  pArray[i].draw();
			}

			//StdDraw.show();
			StdDraw.pause(10);
			t1 = t1 + dt;

		}
	}
}
