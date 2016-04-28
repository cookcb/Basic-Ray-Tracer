package Assignment1;

public class Spheres {
	float x, y, z, radius;
	public Spheres(float x, float y, float z, float radius){
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
	}
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
	public float intersect(Vectors Direction,  Vectors viewPoint){
		float A, B, C, discrim;
		//Vectors V = new Vectors(-1.0f * this.getX() , -1.0f * this.getY(), -1.0f * this.getZ());

		A = (Direction.getX() * Direction.getX()) + (Direction.getY() * Direction.getY()) + (Direction.getZ() * Direction.getZ());

		B = 2 * ((Direction.getX()*(viewPoint.getX()-this.getX())) + (Direction.getY() * (viewPoint.getY() - this.getY())) + (Direction.getZ() * (viewPoint.getZ() - this.getZ())));

		C = ((viewPoint.getX() - this.getX()) * (viewPoint.getX() - this.getX())) + ((viewPoint.getY() - this.getY()) * (viewPoint.getY() - this.getY())) + ((viewPoint.getZ() - this.getZ()) * (viewPoint.getZ() - this.getZ())) - (this.getRadius() * this.getRadius());

		discrim = (B * B) - 4*A*C;
		
		return discrim;
	}
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