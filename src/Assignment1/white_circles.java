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

public class white_circles extends JFrame implements GLEventListener {



	final int width = 512;
	final int height = 512;
	Buffer scene;


	public white_circles() {

		super("White Circles");
		this.scene = renderScene();

		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities caps = new GLCapabilities(profile);

		GLCanvas canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);

		this.setName("White Circles");
		this.getContentPane().add(canvas);

		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		canvas.requestFocusInWindow();
	}

	public Buffer renderScene() {
		Vectors e = new Vectors(0, 0, 0);
		//All the objects
		Spheres green = new Spheres(0, 0, -7, 2, 32.0f, new Vectors(0, 0, 0), new Vectors(0, 0, 0), new Vectors(0, 0, 0));
		Spheres blue = new Spheres(4, 0, -7, 1, 0, new Vectors(0, 0, 0), new Vectors(0, 0, 0), new Vectors(0, 0, 0));
		Spheres red = new Spheres(-4, 0, -7, 1, 0, new Vectors(0, 0, 0), new Vectors(0, 0, 0), new Vectors(0, 0, 0));
		float[] pixelValues = new float[width * height * 3];
		float d = 0.1f;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int i = (y * height) + x;
				i *= 3;

				float U = x / 512f;
				float V = y / 512f;

				U = U * (float) 0.2;
				V = V * (float) 0.2;

				U = (U - 0.1f);
				V = (V - 0.1f);
				//Viewing Ray
				Vectors D = new Vectors(U, V, -d);
				D = D.unitV();
				Ray eyeRay = new Ray(D, e);

				//Calculating intersection points
				float disc1 = green.getT(eyeRay.getDirection(), eyeRay.getPoint());
				float disc2 = blue.getT(eyeRay.getDirection(), eyeRay.getPoint());
				float disc3 = red.getT(eyeRay.getDirection(), eyeRay.getPoint());

				if(disc1 > 0){            		
					pixelValues[i + 0] = 255; // red
					pixelValues[i + 1] = 255; // green
					pixelValues[i + 2] = 255; // blue
				}else if(disc2 > 0){
					pixelValues[i + 0] = 255; // red
					pixelValues[i + 1] = 255; // green
					pixelValues[i + 2] = 255; // blue
				}else if(disc3 > 0){
					pixelValues[i + 0] = 255; // red
					pixelValues[i + 1] = 255; // green
					pixelValues[i + 2] = 255; // blue	
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
		white_circles basicCircles = new white_circles();
	}
}
