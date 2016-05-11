package Assignment1;

public class Plane implements Surfaces{
	Vectors normal, ka, kd, ks, pointOn;
	float alpha, SP;
	public Plane(Vectors normal, float SP, Vectors ka, Vectors kd, Vectors ks, Vectors pointOn){
		this.normal = normal;
		this.ka = ka;
		this.kd = kd;
		this.ks = ks;
		this.SP = SP;
		this.pointOn = pointOn;
	}
	public float getSpecular(){
		return SP;
	}
	//Obtains the Ambient, Specular, and Diffuse factors
	public Vectors getKA(){
		return ka;
	}
	public Vectors getKD(){
		return kd;
	}
	public Vectors getKS(){
		return ks;
	}
	//Obtains the closest value at which the direction vector hits the plane
	public float getT(Vectors Direction, Vectors viewPoint){
		float T;
		if( (float) Direction.dot(normal)==0){
			return -1f;
		}
		T = (float) pointOn.sub(viewPoint).dot(normal) / (float) Direction.dot(normal);
		return T;
		
	}
	//Retrieves the Normal vector for the plane
	public Vectors getNormal(Vectors intersection) {
		return normal;
	}
}
