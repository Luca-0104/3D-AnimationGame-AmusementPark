package objects3D;

import org.lwjgl.opengl.GL11;
import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;
import java.math.*;

public class Cylinder {

	
	public Cylinder() { 
	}
	
	// remember to use Math.PI isntead PI 
	// Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8 
	public void DrawCylinder(float radius, float height, int nSegments) {

		GL11.glBegin(GL11.GL_TRIANGLES);

		//loop around the circumference of the tube
		for(int i = 0; i < nSegments; i++){

			//calculate the angle from the start vertex to the current vertx on the circumference (the polygon) of the tube
			float angle = (float) (Math.PI * i * 2.0 / nSegments);
			//calculate the angle from the start vertex to the next vertx on the circumference (the polygon) of the tube
			float nextAngle = (float) (Math.PI * (i + 1.0) * 2.0 / nSegments);

			//compute the (x, y) of the current vertex(x1, y1) and next vertex(x2, y2) on the polygon
			//further, we multiply the value by 'radius'
			//so that the thickness of the cylinder can be controlled by the value of 'radius'
			float x1 = (float) Math.sin(angle) * radius;
			float y1 = (float) Math.cos(angle) * radius;
			float x2 = (float) Math.sin(nextAngle) * radius;
			float y2 = (float) Math.cos(nextAngle) * radius;

			//draw the upper triangle
			GL11.glNormal3f(x1, y1, 0);	GL11.glVertex3f(x1, y1, 0);
			GL11.glNormal3f(x2, y2, 0);	GL11.glVertex3f(x2, y2, height);
			GL11.glNormal3f(x1, y1, 0);	GL11.glVertex3f(x1, y1, height);

			//draw the lower triangle
			GL11.glNormal3f(x1, y1, 0);	GL11.glVertex3f(x1, y1, 0);
			GL11.glNormal3f(x2, y2, 0);	GL11.glVertex3f(x2, y2, 0);
			GL11.glNormal3f(x2, y2, 0);	GL11.glVertex3f(x2, y2, height);

			//draw the top cover
			GL11.glNormal3f(0, 0, height);	GL11.glVertex3f(x1, y1, height);
			GL11.glNormal3f(0, 0, height);	GL11.glVertex3f(x2, y2, height);
			GL11.glNormal3f(0, 0, height);	GL11.glVertex3f(0, 0, height);

			//draw the bottom cover
			GL11.glNormal3f(0, 0, -height);	GL11.glVertex3f(x1, y1, 0);
			GL11.glNormal3f(0, 0, -height);	GL11.glVertex3f(x2, y2, 0);
			GL11.glNormal3f(0, 0, -height);	GL11.glVertex3f(0, 0, 0);

		}

		GL11.glEnd();
	}
}
