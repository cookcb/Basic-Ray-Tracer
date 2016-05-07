package Assignment1;

public class Ray {
	Vectors direction, point;
	public Ray(Vectors direction, Vectors point){
		this.direction = direction;
		this.point = point;
	}

	public Vectors getDirection(){
		return this.direction;
	}
	public Vectors getPoint(){
		return this.point;
	}
	public void normalizeD(){
		this.direction = direction.unitV();
	}
}
