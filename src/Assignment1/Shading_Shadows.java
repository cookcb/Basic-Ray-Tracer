package Assignment1;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.JFrame;

public class Shading_Shadows extends JFrame implements GLEventListener {

	final int width = 512;
	final int height = 512;

	Buffer scene;
	public final static int  dimension = 512;
	public float l = -0.1f, r = 0.1f, b = -0.1f, t = 0.1f, d = 0.1f, A, B, C, A2, B2, C2, A3, B3, C3;
	float U, V;
	public Vectors u, v, w, e, D, ray;
	public Spheres green, blue, red;

	public Shading_Shadows() {

		super("Shading");
		this.scene = renderScene();

		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities caps = new GLCapabilities(profile);

		GLCanvas canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);

		this.setName("Shading");
		this.getContentPane().add(canvas);

		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		canvas.requestFocusInWindow();
	}

	public Buffer renderScene() {
		u = new Vectors(1, 0, 0);
		v = new Vectors(0, 1, 0);
		w = new Vectors(0, 0, 1);
		e = new Vectors(0, 0, 0);
		Vectors light = new Vectors(-4, 4, -3);
		green = new Spheres(0, 0, -7, 2);
		blue = new Spheres(4, 0, -7, 1);
		red = new Spheres(-4, 0, -7, 1);
		float[] pixelValues = new float[width * height * 3];


		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int i = (y * height) + x;
				i *= 3;
				U = x / 512f;
				V = y / 512f;
				U = U * (float) 0.2;
				V = V * (float) 0.2;
				U = (U - 0.1f);
				V = (V - 0.1f);
				Vectors D = new Vectors(U, V, -d);

				D = D.unitV();


				float disc1 = green.intersect(D, e);
				float disc2 = blue.intersect(D, e);
				float disc3 = red.intersect(D, e);

				if(disc1 > 0){            		
					float t = green.getT(D, e);              		
					float max, max2; 

					Vectors intsecP = D.ScalarMult(t);   


					Vectors View = new Vectors(e.getX() - intsecP.getX(), e.getY() - intsecP.getY(), e.getZ() - intsecP.getZ());
					View = D.invert();
					
					View = View.unitV();
					Vectors l = light.sub(intsecP);

					l = l.unitV();

					Vectors n = new Vectors((intsecP.getX() - green.getX()), 
							(intsecP.getY() - green.getY()), 
							(intsecP.getZ() - green.getZ()));              		
					n = n.unitV();       

					Vectors Ka = new Vectors(0.0f, 0.2f, 0.0f);
					Vectors Kd = new Vectors(0.0f, 0.5f, 0.0f);
					Vectors Ks = new Vectors(0.5f, 0.5f, 0.5f);   

					if(n.dot(l) < 0){            			
						max = 0.0f;
					}else{
						max = (float) n.dot(l);            			
					} 

					Vectors Ld = new Vectors(Kd.getX() * max, Kd.getY() * max, Kd.getZ() * max);
					Vectors h = new Vectors(View.getX() + l.getX(), View.getY() + l.getY(), View.getZ() + l.getZ());

					h = h.unitV();            		            		
					if(n.dot(h) < 0){
						max2 = 0;
					}else{
						max2 = (float) n.dot(h);
					}
					Vectors L;
					if(red.intersect(l, intsecP) > 0){
						L = Ka;
					}else{       

						Vectors Ls = Ks.ScalarMult((float) Math.pow(max2, 32));              		
						Vectors La = Ka;            		
						L = Ld;
						L = L.add(La);
						L = L.add(Ls);
					}

					pixelValues[i + 0] = (float) Math.pow(L.getX(), 1.0 /2.2f); 
					pixelValues[i + 1] = (float) Math.pow(L.getY(), 1.0 /2.2f); 
					pixelValues[i + 2] = (float) Math.pow(L.getZ(), 1.0 /2.2f);
				}else if(disc2 > 0){
					float t = blue.getT(D, e);              		
					float SP = 0.0f, max, max2; 

					Vectors intsecP = D.ScalarMult(t);   
					Vectors View = new Vectors(intsecP.getX() * -1f, intsecP.getY() * -1f, intsecP.getZ() * -1f);
					Vectors l = light.sub(intsecP);
					l = l.unitV();  
					Vectors n = new Vectors((intsecP.getX() - blue.getX()) / blue.getRadius(), 
							(intsecP.getY() - blue.getY()) / blue.getRadius(), 
							(intsecP.getZ() - blue.getZ()) / blue.getRadius());              		
					n = n.unitV();       

					Vectors Ka = new Vectors(0, 0, 0.2f);
					Vectors Kd = new Vectors(0, 0, 1);
					Vectors Ks = new Vectors(0, 0, 0);            		
					if(n.dot(l) < 0){            			
						max = 0.0f;
					}else{
						max = (float) n.dot(l);            			
					}            		
					Vectors Ld = Kd.ScalarMult(max);      		
					Vectors h = View.add(l);
					h = h.unitV();            		            		
					if(n.dot(h) < 0){
						max2 = 0;
					}else{
						max2 = (float) n.dot(h);
					}
					Vectors L;
					if(red.intersect(l, intsecP) > 0 || green.intersect(l, intsecP) > 0){
						L = Ka;
					}else{

						Vectors Ls = Ks.ScalarMult((float) Math.pow(max2, SP));              		
						Vectors La = Ka;            		
						L = La.add(Ld);
						L = L.add(Ls);
					}


					pixelValues[i + 0] = (float) Math.pow(L.getX(), 1.0 /2.2f); 
					pixelValues[i + 1] = (float) Math.pow(L.getY(), 1.0 /2.2f); 
					pixelValues[i + 2] = (float) Math.pow(L.getZ(), 1.0 /2.2f);
				}else if(disc3 > 0){
					float t = red.getT(D, e);              		
					float SP = 0.0f, max, max2; 

					Vectors intsecP = D.ScalarMult(t);           		            		 
					Vectors l = light.sub(intsecP);
					l = l.unitV(); 

					Vectors n = new Vectors((intsecP.getX() - red.getX()) / red.getRadius(), 
							(intsecP.getY() - red.getY()) / red.getRadius(), 
							(intsecP.getZ() - red.getZ()) / red.getRadius());              		
					n = n.unitV();       

					Vectors Ka = new Vectors(0.2f, 0, 0);
					Vectors Kd = new Vectors(1, 0, 0);
					Vectors Ks = new Vectors(0, 0, 0);           		
					if(n.dot(l) < 0){            			
						max = 0.0f;
					}else{
						max = (float) n.dot(l);            			
					}            		
					Vectors Ld = Kd.ScalarMult(max);      		
					Vectors h = D.add(l);

					h = h.unitV();            		            		
					if(n.dot(h) < 0){
						max2 = 0;
					}else{
						max2 = (float) n.dot(h);
					}
					Vectors Ls = Ks.ScalarMult((float) Math.pow(max2, SP));  

					Vectors La = Ka;            		
					Vectors L = La.add(Ld);
					L = L.add(Ls); 



					pixelValues[i + 0] = (float) Math.pow(L.getX(), 1.0 /2.2f); 
					pixelValues[i + 1] = (float) Math.pow(L.getY(), 1.0 /2.2f); 
					pixelValues[i + 2] = (float) Math.pow(L.getZ(), 1.0 /2.2f);
				}else{
					Vectors Ka = new Vectors(0.2f, 0.2f, 0.2f);
					Vectors Kd = new Vectors(1f, 1f, 1f);
					Vectors Ks = new Vectors(0f, 0f, 0f); 

					Vectors normal = new Vectors(0, 1 ,0);
					normal = normal.unitV();


					float T = -2 / D.getY();


					if(T >= 0){

						float max, max2;

						Vectors intsecP = new Vectors(D.getX() * T, D.getY() * T, D.getZ() * T);


						Vectors l = light.sub(intsecP);
						l = l.unitV();


						if(normal.dot(l) < 0){            			
							max = 0.0f;
						}else{
							max = (float) normal.dot(l);            			
						}            		
						Vectors Ld = Kd.ScalarMult(max);  
						Vectors h = intsecP.invert().add(l);

						h = h.unitV();            		            		
						if(normal.dot(h) < 0){
							max2 = 0;
						}else{
							max2 = (float) normal.dot(h);
						}
						Vectors L = new Vectors(0, 0, 0);

						if(red.intersect(l, intsecP) > 0 || green.intersect(l, intsecP) > 0 || blue.intersect(l, intsecP) > 0){
							Vectors La = Ka;
							L = La;
						}else{
							Vectors Ls = Ks.ScalarMult((float) Math.pow(max2, 0));              		
							Vectors La = Ka;            		
							L = La.add(Ld);
							L = L.add(Ls);
						}
						pixelValues[i + 0] = (float) Math.pow(L.getX(), 1.0 /2.2f); 
						pixelValues[i + 1] = (float) Math.pow(L.getY(), 1.0 /2.2f); 
						pixelValues[i + 2] = (float) Math.pow(L.getZ(), 1.0 /2.2f);
					}else{
						pixelValues[i + 0] = 0; 
						pixelValues[i + 1] = 0; 
						pixelValues[i + 2] = 0; 
					}

				}   
			}
		}
		return FloatBuffer.wrap(pixelValues);

	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glDrawPixels(width, height, GL2.GL_RGB, GL2.GL_FLOAT, this.scene);

		gl.glFlush();
	}

	public void dispose(GLAutoDrawable arg0) {

	}

	public void init(GLAutoDrawable arg0) {

	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {

	}

	public static void main(String[] args) {
		Shading_Shadows shading = new Shading_Shadows();
	}
}
