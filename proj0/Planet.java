import java.lang.Math;

public class Planet{
	double xxPos;
	double yyPos;
	double xxVel;
	double yyVel;
	double mass;
	String imgFileName;
	static final double G = 6.67e-11;

	public Planet(double xP, double yP, double xV,
              double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;

	}

	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p){

		double rrDistance = Math. sqrt(
		Math.pow((xxPos - p.xxPos),2) +
		Math.pow((yyPos - p. yyPos),2));

		//System.out.println( rrDistance);
		return rrDistance;
	}

	public double calcForceExertedBy(Planet p){

		double ffForce = (G*mass*p.mass)/(Math.pow(calcDistance(p),2));
		//System.out.println( rrForce );
		return ffForce;
	}

	public double calcForceExertedByX(Planet p){
		if ((p.xxPos - xxPos) >= 0){
		double xxForce = calcForceExertedBy(p)*
		((xxPos - p.xxPos)/calcDistance(p));
		}
		double xxForce = - (calcForceExertedBy(p)*
		((xxPos - p.xxPos)/calcDistance(p)));
		return xxForce;
	}

	public double calcForceExertedByY(Planet p){
		if ((p.yyPos - yyPos) >= 0){
		double yyForce = calcForceExertedBy(p)*
		((yyPos - p.yyPos)/calcDistance(p));
		}
		double yyForce = - (calcForceExertedBy(p)*
		((yyPos - p.yyPos)/calcDistance(p)));
		return yyForce;
	}

	public double calcNetForceExertedByX(Planet[] allp){
		double xxNetForce = 0;
		for (int i = 0 ; i < allp.length ; i +=1){
			if (allp[i].equals(this)){
				continue;
			}
			xxNetForce = xxNetForce + this.calcForceExertedByX(allp[i]);
		}
		return xxNetForce;
	}

	public double calcNetForceExertedByY(Planet[] allp){
		double yyNetForce = 0;
		for (int i = 0 ; i < allp.length ; i +=1){
			if (allp[i].equals(this)){
				continue;
			}
			yyNetForce = yyNetForce + this.calcForceExertedByY(allp[i]);
		}
		return yyNetForce;
	}

	public void update(double dt, double fX, double fY){
		xxVel = xxVel + dt*( fX / mass);
		yyVel = yyVel + dt*( fY / mass);
		xxPos = xxPos + dt*xxVel;
		yyPos = yyPos + dt*yyVel;
	}

}
