package Assignment1;

public class Vectors {
	float x, y, z;
	public Vectors(float X, float Y, float Z){
		this.x = X;
		this.y = Y;
		this.z = Z;
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
	public void setX(float newX){
		this.x = newX;
	}
	public void setY(float newY){
		this.y = newY;
	}
	public void setZ(float newZ){
		this.z = newZ;
	}
	public Vectors invert(){
		Vectors Vec;
		Vec = new Vectors(-1.0f * this.getX(), -1.0f * this.getY(), -1.0f * this.getZ());

		return Vec;
	}
	public Vectors add(Vectors v){
		Vectors addedVector = new Vectors(v.getX() + this.getX(), 
				v.getY() + this.getY(), 
				v.getZ() + this.getZ());
		return addedVector;
	}
	public Vectors sub(Vectors v){
		Vectors subVector = new Vectors(this.getX() - v.getX(), 
				this.getY() - v.getY(), 
				this.getZ() - v.getZ());
		return subVector;
	}
	public float dot(Vectors v){
		float dot;
		dot = (x * v.getX());
		dot = dot + (y * v.getY());
		dot = dot + (z * v.getZ());
		return dot; 
	}
	public Vectors ScalarDiv(float scalar){
		float x, y, z;
		x = (float) this.getX() / scalar;
		y = (float) this.getY() / scalar;
		z = (float) this.getZ() / scalar;

		return new Vectors(x, y, z);

	}
	public Vectors ScalarMult(float scalar){
		float x, y, z;
		x = (float) this.getX() * scalar;
		y = (float) this.getY() * scalar;
		z = (float) this.getZ() * scalar;

		return new Vectors(x, y, z);
	}
	public Vectors unitV(){
		float newX = (float) (this.getX() / Math.sqrt((( this.getX() * this.getX()) + (this.getY() * this.getY()) + (this.getZ() * this.getZ()))));
		float newY = (float) (this.getY() / Math.sqrt(( this.getX() *  this.getX()) + (this.getY() * this.getY()) + (this.getZ() * this.getZ())));
		float newZ = (float) (this.getZ() / Math.sqrt(( this.getX() *  this.getX()) + (this.getY() *  this.getY()) + ( this.getZ() * this.getZ())));
		return new Vectors(newX, newY, newZ);

	}
	public float magnitude(){
		float length = (float) Math.sqrt((this.getX() * this.getX()) + (this.getY() * this.getY()) + (this.getZ() * this.getZ()));
		return length;
	}
	public Vectors Cross(Vectors V){
		Vectors CrossedV;
		CrossedV = new Vectors((float) (this.getY()*V.getZ()) - (this.getZ()*V.getY()),
				(float) (this.getZ()*V.getX()) - (this.getX()*V.getZ()),
				(float) (this.getX()*V.getY()) - (this.getY()*V.getX()));
		return CrossedV;

	}
}
