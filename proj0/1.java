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

	public double calDistance(Planet p){
		
		double rrDistance = Math. sqrt( 
		Math.pow((xxPos - p.xxPos),2) + 
		Math.pow((yyPos - p. yyPos),2));
		
		//System.out.println( rrDistance);
		return rrDistance;
	}

	public double calcForceExertedBy(Planet p){	
	
		double ffForce = (G*mass*p.mass)/(Math.pow(calDistance(p),2));
		//System.out.println( rrForce );
		return ffForce;
	}
	public double calcForceExertedByX(Planet p){
		double xxForce = calcForceExertedBy(p)*
		((xxPos - p.xxPos)/calDistance(p));
		return xxForce;
	}

	public double calcForceExertedByY(Planet p){
		double yyForce = calcForceExertedBy(p)*
		((yyPos - p.yyPos)/calDistance(p));
		return yyForce;
	}
}