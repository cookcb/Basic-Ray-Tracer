package Assignment1;

public interface Surfaces {
	public Vectors getKS();
	public Vectors getKA();
	public Vectors getKD();
	public float getT(Vectors Direction, Vectors viewPoint);
	public Vectors getNormal(Vectors intersection);
	public float getSpecular();
}
