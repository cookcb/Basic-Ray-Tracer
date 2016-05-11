package Assignment1;


public class Spheres implements Surfaces{
	float x, y, z, radius, SP;
	Vectors ka, kd, ks;
	public Spheres(float x, float y, float z, float radius, float SP, Vectors ka, Vectors kd, Vectors ks){
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.SP = SP;
		this.ka = ka;
		this.kd = kd;
		this.ks = ks;
	}
	//X, Y, Z values for the center of the sphere
	public float getX(){
		return this.x;
	}
	public float getY(){
		return this.y;
	}
	public float getZ(){
		return this.z;
	}
	
	public float getRadius(){
		return this.radius;
	}
	
	//Retrieves the specular factor
	public Vectors getKS() {
		return ks;
	}
	//Retrieves the Ambient factor
	public Vectors getKA() {
		return ka;
	}
	//Retrieves the Diffuse factor
	public Vectors getKD() {
		return kd;
	}
	
	//Obtains normal vector from that goes through a point on the sphere
	public Vectors getNormal(Vectors intersection) {
		Vectors normal;
		normal = new Vectors((intersection.getX() - this.getX()), 
				(intersection.getY() - this.getY()), 
				(intersection.getZ() - this.getZ()));
		return normal;
	}
	//retrieves the specular power value
	public float getSpecular() {
		
		return SP;
	}
	//Obtains the closest value at which the direction vector hits the sphere 
	public float getT(Vectors Direction,  Vectors viewPoint){
		float t1, t2, result = 0;
		float A, B, C;

		A = (Direction.getX() * Direction.getX()) + (Direction.getY() * Direction.getY()) + (Direction.getZ() * Direction.getZ());

		B = 2.0f * ((float)(Direction.getX()*(viewPoint.getX()-this.getX())) + (float)(Direction.getY() * (viewPoint.getY() - this.getY())) + (float)(Direction.getZ() * (viewPoint.getZ() - this.getZ())));

		C = ((viewPoint.getX() - this.getX()) * (viewPoint.getX() - this.getX())) + ((viewPoint.getY() - this.getY()) * (viewPoint.getY() - this.getY())) + ((viewPoint.getZ() - this.getZ()) * (viewPoint.getZ() - this.getZ())) - (this.getRadius() * this.getRadius());
		t1 = (float) (((float)-B + (float) Math.sqrt((B*B) - (4.0f*A*C)))/ (2.0f * A));
		t2 = (float) (((float)-B - (float) Math.sqrt((B*B) - (4.0f*A*C)))/ (2.0f * A));
		if(t1 > t2){
			result = t2;
		}else{
			result = t1;
		}
		return result;

	}
	
	

}
