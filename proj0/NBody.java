
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
		StdDraw.enableDoubleBuffering();
    	StdDraw.picture(0, 0, backgroud);
		for (Planet p : pArray){
		p.draw();
		}

		double t1 = 0;
		while (t1 <= T){
			double [] xForces = new double [pArray.length];
			double [] yForces = new double [pArray.length];
			for (int i =0; i < pArray.length; i+=1 ){
				double xForce = pArray[i].calcNetForceExertedByX(pArray);
				double yforce = pArray[i].calcNetForceExertedByY(pArray);
				xForces[i] = xForce;
				yForces[i] = yforce;
			}

			for (int i =0; i < pArray.length; i+=1 ){
				pArray[i].update(dt,xForces[i], yForces[i]);
			}
			StdDraw.picture(0, 0, backgroud);
			for (Planet p : pArray){
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			t1 = t1 + dt;

		}
		StdOut.printf("%d\n", pArray.length);
		StdOut.printf("%.2e\n", r);
		for (int i = 0; i < pArray.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  pArray[i].xxPos, pArray[i].yyPos, pArray[i].xxVel,
                  pArray[i].yyVel, pArray[i].mass, pArray[i].imgFileName);   
		}
	}
}
