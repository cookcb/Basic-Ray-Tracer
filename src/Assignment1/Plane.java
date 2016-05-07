package Assignment1;

public class Plane implements Surfaces{
	Vectors normal, ambient, kd, ks, pointOn;
	float alpha;
	public Plane(Vectors normal, Vectors ambient, Vectors kd, Vectors ks, float alpha, Vectors pointOn){
		this.normal = normal;
		this.ambient = ambient;
		this.kd = kd;
		this.ks = ks;
		this.alpha = alpha;
		this.pointOn = pointOn;
	}
	public float getSpecular(){
		return 0;
	}
	public float getAlpha(){
		return alpha;
	}
	public Vectors getAmbient(){
		return ambient;
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
