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

	//Size of JFrame/Image
	final int width = 512;
	final int height = 512;

	Buffer scene;
	Vectors lightSource = new Vectors(-4, 4, -3);	
	//Creation of different objects
	Spheres green = new Spheres(0, 0, -7, 2, 32.0f, new Vectors(0.0f, 0.2f, 0.0f), new Vectors(0.0f, 0.5f, 0.0f), new Vectors(0.5f, 0.5f, 0.5f));
	Spheres blue = new Spheres(-4 , 0, -7, 1, 0, new Vectors(0.2f, 0, 0), new Vectors(1, 0, 0), new Vectors(0, 0, 0));
	Spheres red = new Spheres(4, 0, -7, 1, 0, new Vectors(0, 0, 0.2f), new Vectors(0, 0, 1), new Vectors(0, 0, 0));
	Plane plane = new Plane(new Vectors(0, 1 ,0), 0, new Vectors(0.2f, 0.2f, 0.2f), new Vectors(1f, 1f, 1f), new Vectors(0f, 0f, 0f), new Vectors(0, -2, 0));
	
	//Array to hold the 4 different objects
	Surfaces[] objects = {green, red, blue, plane};

	public Anti_Aliasing() {
		//Creation of JFrame
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
	//Creation/Rendering of Scene
	public Buffer renderScene() {
		float d = 0.1f;
		float U, V;
		float[] pixelValues = new float[width * height * 3];
		//eye point
		Vectors e = new Vectors(0, 0, 0);
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

					float closest = Integer.MAX_VALUE;
					Surfaces surf = null;
					//Calculate the closest object the viewing ray hits
					for(int k = 0; k < objects.length; k++){
						float T = objects[k].getT(D, e);
						if(T >= 0 && T <= closest){
							closest = T;
							surf = objects[k];
						}
					}
					if(surf == null){

					}else{
						Vectors intsecP;
						//The point at which the object is hit
						intsecP = D.ScalarMult(surf.getT(D, e));
						intsecP = intsecP.add(e);
						//Vector coming from light source
						Vectors light = lightSource.sub(intsecP);
						light = light.unitV();
						Vectors norm = surf.getNormal(intsecP);
						norm = norm.unitV();
						//Ray going from eye position to the hit object using normalized direction
						Ray eyeRay;
						eyeRay = new Ray(D.invert(), e);
						eyeRay.normalizeD();

						//Calculation of blin-phong shadeing with specular, diffuse and ambient
						Vectors shadeing = Phong(surf, eyeRay, norm, intsecP, light, surf.getKA(), surf.getKD(), surf.getKS(), surf.getSpecular());

						//Add each color value with gamma of 2.2
						pixelValues[i + 0] = pixelValues[i + 0] + (float) Math.pow(shadeing.getX(), 1.0 /2.2f); // red
						pixelValues[i + 1] = pixelValues[i + 1] + (float) Math.pow(shadeing.getY(), 1.0 /2.2f); // green
						pixelValues[i + 2] = pixelValues[i + 2] + (float) Math.pow(shadeing.getZ(), 1.0 /2.2f); // blue
					}
				}            	
			}
		}
		//To average out the pixel values for anti-aliasing
		for(int w = 0; w < width * height * 3; w++){
			pixelValues[w] = pixelValues[w] / 64;
		}
		return FloatBuffer.wrap(pixelValues);

	}
	public Vectors Phong(Surfaces object, Ray eyeRay, Vectors normal, Vectors intsecP, Vectors light, Vectors ambient, Vectors diffuse, Vectors specular, float SP){
		Vectors phong = new Vectors(0, 0, 0);
		boolean shadows = false;

		//Checks to see if the pixel is in shadow
		for(int i = 0; i < objects.length; i++){
			if(object == objects[i]){

			}else if(objects[i].getT(light, intsecP) > 0){
				shadows = true;
			}
		}

		if(shadows == true){
			//Only apply ambient if pixel is in shadow
			phong = ambient;
		}else{
			float max, max2;
			//Calculations for diffuse shadeing
			max = (float) Math.max(0, normal.dot(light));
			Vectors Ld = diffuse.ScalarMult(max);      		

			//Calculations for specular highlight
			Vectors h = eyeRay.getDirection().add(light).unitV();           		            		
			max2 = (float) Math.max(0, normal.dot(h));
			Vectors Ls = specular.ScalarMult((float) Math.pow(max2, object.getSpecular()));           		

			//Combining all three shadeing parameters
			phong = ambient.add(Ld).add(Ls);
		}
		return phong;
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
		Anti_Aliasing AA = new Anti_Aliasing();
	}
}


