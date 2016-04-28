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
	public final static int  dimension = 512;
	public float l = -0.1f, r = 0.1f, b = -0.1f, t = 0.1f, d = 0.1f, A, B, C, A2, B2, C2, A3, B3, C3;
	float U, V;
	public Vectors u, v, w, e, D, ray;
	public Spheres center, right, left;

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
		u = new Vectors(1, 0, 0);
		v = new Vectors(0, 1, 0);
		w = new Vectors(0, 0, 1);
		e = new Vectors(0, 0, 0);

		center = new Spheres(0, 0, -7, 2);
		right = new Spheres(4, 0, -7, 1);
		left = new Spheres(-4, 0, -7, 1);
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

				float disc1 = center.intersect(D, e);
				float disc2 = right.intersect(D, e);
				float disc3 = left.intersect(D, e);

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
				}else{
					pixelValues[i + 0] = 0; // red
					pixelValues[i + 1] = 0; // green
					pixelValues[i + 2] = 0; // blue
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