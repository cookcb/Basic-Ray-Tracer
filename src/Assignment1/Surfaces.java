package Assignment1;

public interface Surfaces {
	public Vectors getKS();
	public Vectors getAmbient();
	public Vectors getKD();
	public float getAlpha();
	public float getT(Vectors Direction, Vectors viewPoint);
	public float intersect(Vectors Direction,  Vectors viewPoint);
	public Vectors getNormal(Vectors intersection);
	public float getSpecular();
}
