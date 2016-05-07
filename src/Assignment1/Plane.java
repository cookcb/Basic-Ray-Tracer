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
		this.alpha = alpha;
		this.pointOn = pointOn;
	}
	public float getSpecular(){
		return SP;
	}
	public float getAlpha(){
		return alpha;
	}
	public Vectors getKA(){
		return ka;
	}
	public Vectors getKD(){
		return kd;
	}
	public Vectors getKS(){
		return ks;
	}
	public float getT(Vectors Direction, Vectors viewPoint){
		float T;
		if( (float) Direction.dot(normal)==0){
			return -1f;
		}
		T = (float) pointOn.sub(viewPoint).dot(normal) / (float) Direction.dot(normal);
		return T;
		
	}
	public Vectors getNormal(Vectors intersection) {
		return normal;
	}
	public float intersect(Vectors Direction, Vectors viewPoint) {
		float T;
		if( (float) Direction.dot(normal)==0){
			return -1f;
		}
		T = (float) pointOn.sub(viewPoint).dot(normal) / (float) Direction.dot(normal);
		return T;
	}
	

}
