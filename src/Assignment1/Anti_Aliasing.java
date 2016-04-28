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
public class Anti_Aliasing extends JFrame implements GLEventListener{



	final int width = 512;
	final int height = 512;

	Buffer scene;
	public final static int  dimension = 512;
	public float l = -0.1f, r = 0.1f, b = -0.1f, t = 0.1f, d = 0.1f, A, B, C, A2, B2, C2, A3, B3, C3;
	float U, V;
	public Vectors u, v, w, e, D, ray;
	public Spheres center, right, left;

	public Anti_Aliasing() {

		super("Anti Aliasing");
		this.scene = renderScene();

		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities caps = new GLCapabilities(profile);

		GLCanvas canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);

		this.setName("Anti Aliasing");
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
		center = new Spheres(0, 0, -7, 2);
		right = new Spheres(4, 0, -7, 1);
		left = new Spheres(-4, 0, -7, 1);
		float[] pixelValues = new float[width * height * 3];
		int count = 64;

		for(int q = 0; q < count; q++){
			Random rand = new Random();
			Random rand2 = new Random();

			float maxX = .2f / 512f;
			float minY = -.2f / 512f;

			float finalX = (maxX - minY) * rand.nextFloat() + maxX;
			float finalY = (maxX - minY) * rand2.nextFloat() + maxX;

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
					U = U + finalX;
					V = V + finalY;
					Vectors D = new Vectors(U, V, -d);

					D = D.unitV();

					float disc1 = center.intersect(D, e);
					float disc2 = right.intersect(D, e);
					float disc3 = left.intersect(D, e);

					if(disc1 > 0){            		
						float t = center.getT(D, e);              		
						float max, max2; 

						Vectors intsecP = D.ScalarMult(t);   


						Vectors View = new Vectors(e.getX() - intsecP.getX(), e.getY() - intsecP.getY(), e.getZ() - intsecP.getZ());
						View = View.unitV();
						Vectors l = light.sub(intsecP);

						l = l.unitV();


						Vectors n = new Vectors((intsecP.getX() - center.getX()), 
								(intsecP.getY() - center.getY()), 
								(intsecP.getZ() - center.getZ()));              		
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
						if(left.intersect(l, intsecP) > 0){
							L = Ka;
						}else{       

							Vectors Ls = Ks.ScalarMult((float) Math.pow(max2, 32));              		
							Vectors La = Ka;            		
							L = Ld;
							L = L.add(La);
							L = L.add(Ls);
						}




						pixelValues[i + 0] = pixelValues[i + 0] + L.getX(); // red
						pixelValues[i + 1] = pixelValues[i + 1] + L.getY(); // green
						pixelValues[i + 2] = pixelValues[i + 2] + L.getZ(); // blue
					}else if(disc2 > 0){
						float t = right.getT(D, e);              		
						float SP = 0.0f, max, max2; 

						Vectors intsecP = D.ScalarMult(t);   
						Vectors View = new Vectors(intsecP.getX() * -1f, intsecP.getY() * -1f, intsecP.getZ() * -1f);
						Vectors l = light.sub(intsecP);
						l = l.unitV(); 


						Vectors n = new Vectors((intsecP.getX() - right.getX()) / right.getRadius(), 
								(intsecP.getY() - right.getY()) / right.getRadius(), 
								(intsecP.getZ() - right.getZ()) / right.getRadius());              		
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
						if(left.intersect(l, intsecP) > 0 || center.intersect(l, intsecP) > 0){
							L = Ka;
						}else{

							Vectors Ls = Ks.ScalarMult((float) Math.pow(max2, SP));              		
							Vectors La = Ka;            		
							L = La.add(Ld);
							L = L.add(Ls);
						}



						pixelValues[i + 0] = pixelValues[i + 0] + L.getX(); // red
						pixelValues[i + 1] = pixelValues[i + 1] + L.getY(); // green
						pixelValues[i + 2] = pixelValues[i + 2] + L.getZ(); // blue
					}else if(disc3 > 0){
						float t = left.getT(D, e);              		
						float SP = 0.0f, max, max2; 

						Vectors intsecP = D.ScalarMult(t);           		            		 
						Vectors l = light.sub(intsecP);
						l = l.unitV(); 

						Vectors n = new Vectors((intsecP.getX() - left.getX()) / left.getRadius(), 
								(intsecP.getY() - left.getY()) / left.getRadius(), 
								(intsecP.getZ() - left.getZ()) / left.getRadius());              		
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



						pixelValues[i + 0] = pixelValues[i + 0] + L.getX(); // red
						pixelValues[i + 1] = pixelValues[i + 1] + L.getY(); // green
						pixelValues[i + 2] = pixelValues[i + 2] + L.getZ(); // blue	
					}else{
						Vectors Ka = new Vectors(0.2f, 0.2f, 0.2f);
						Vectors Kd = new Vectors(1f, 1f, 1f);
						Vectors Ks = new Vectors(0f, 0f, 0f); 

						Vectors normal = new Vectors(0, 1 ,0);
						normal = normal.unitV();
						Vectors pointOnPlane = new Vectors(0, -2, 0);
						pointOnPlane = pointOnPlane.unitV();

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
							Vectors V = intsecP.invert();
							V.unitV();
							Vectors h = intsecP.invert().add(l);

							h = h.unitV();            		            		
							if(normal.dot(h) < 0){
								max2 = 0;
							}else{
								max2 = (float) normal.dot(h);
							}
							Vectors L;

							if(left.intersect(l, intsecP) > 0 || center.intersect(l, intsecP) > 0 || right.intersect(l, intsecP) > 0){

								L = Ka;
							}else{

								Vectors Ls = Ks.ScalarMult((float) Math.pow(max2, 0));              		
								Vectors La = Ka;            		
								L = La.add(Ld);
								L = L.add(Ls);
							}
							pixelValues[i + 0] = pixelValues[i + 0] + L.getX(); // red
							pixelValues[i + 1] = pixelValues[i + 1] + L.getY(); // green
							pixelValues[i + 2] = pixelValues[i + 2] + L.getZ(); // blue
						}else{

							pixelValues[i + 0] = pixelValues[i + 0] + 0; // red
							pixelValues[i + 1] = pixelValues[i + 1] + 0; // green
							pixelValues[i + 2] = pixelValues[i + 2] + 0; // blue
						}
					}


				}            	
			}

		}
		for(int sandra = 0; sandra < width * height * 3; sandra++){
			pixelValues[sandra] = pixelValues[sandra] / 64;
		}
		return FloatBuffer.wrap(pixelValues);

	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		//Gamma = 2.2
		gl.glEnable(gl.GL_FRAMEBUFFER_SRGB);
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
		Anti_Aliasing AA = new Anti_Aliasing();
	}
}


