package objects3D;

import org.lwjgl.opengl.GL11;

public class Sphere {

	
	public Sphere() {

	}
	// Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8 
	// 7b should be your primary source, we will cover more about circles in later lectures to understand why the code works 
	public void DrawSphere(float radius, float nSlices, float nSegments) {

		float incTheta = (float) ((2.0f * Math.PI) / nSlices);	//the increment pace length of longitude
		float incPhi = (float) (Math.PI / nSegments);			//the increment pace length of latitude

		GL11.glBegin(GL11.GL_QUADS);

		//loop the longitude of a sphere (latitude oriented)
		for (float theta = (float) -Math.PI; theta < Math.PI; theta += incTheta){
			//loop the latitude of a sphere (longitude oriented)
			for(float phi = (float) -(Math.PI / 2.0f); phi < (Math.PI / 2.0f); phi += incPhi){

				//for a given phi, we can get the radius of circle at this latitude. We call it 'rPhi' here.
				float rPhi = calcuRphi(radius, phi);
				//get the rPhi of the point at next-level latitude
				float rPhiNext = calcuRphi(radius, phi + incPhi);

				//also, for a given phi, we can get the z-value of this circle at this latitude.
				float z = calcuZ(radius, phi);
				//get the Z value of the point at next-level latitude
				float zNext = calcuZ(radius, phi + incPhi);

				/*
				 a quad will be formed with 4 points, which have the following position relationship
				 (x3, y3), (x4, y4)
				 (x, y), (x2, y2)
				*/
				//find the (x, y) position on this 2D circle
				float x = calcuX(rPhi, theta);
				float y = calcuY(rPhi, theta);
				//find the (x2, y2) position on this 2D circle
				float x2 = calcuX(rPhi, theta + incTheta);
				float y2 = calcuY(rPhi, theta + incTheta);
				//find the (x3, y3) position on this 2D circle
				float x3 = calcuX(rPhiNext, theta);
				float y3 = calcuY(rPhiNext, theta);
				//find the (x4, y4) position on this 2D circle
				float x4 = calcuX(rPhiNext, theta + incTheta);
				float y4 = calcuY(rPhiNext, theta + incTheta);

				/* draw four points to form a quad basing on the point (x, y) */
				//first point (x, y)
				GL11.glNormal3f(x, y, z);	GL11.glVertex3f(x, y, z);
				//second point (x2, y2)
				GL11.glNormal3f(x2, y2, z);	GL11.glVertex3f(x2, y2, z);
				//third point (x4, y4)
				GL11.glNormal3f(x4, y4, zNext);	GL11.glVertex3f(x4, y4, zNext);
				//fourth point (x3, y3)
				GL11.glNormal3f(x3, y3, zNext);	GL11.glVertex3f(x3, y3, zNext);

			}
		}

		GL11.glEnd();
	}

	/**
	 * Calculating the radius of the specific circle in the specific sphere
	 * @param radius The radius of the sphere
	 * @param phi The latitude angle of current point
	 * @return	The radius of circle at this latitude in the sphere
	 */
	private float calcuRphi(float radius, float phi){
		return (float) (radius * Math.cos(phi));
	}

	/**
	 * Calculating the z value of points with specific latitude in the specific sphere
	 * @param radius The radius of the sphere
	 * @param phi The latitude angle of current point
	 * @return	The z value of the points at this latitude in the sphere
	 */
	private float calcuZ(float radius, float phi){
		return  (float) (radius * Math.sin(phi));
	}

	/**
	 * Calculating the x value of a point on a 2D circle in a specific sphere
	 * @param rPhi The radius of the circle at a specific latitude in the sphere
	 * @param theta The longitude angle of current point
	 * @return	the x value of a point on a 2D circle
	 */
	private float calcuX(float rPhi, float theta){
		return  (float) (rPhi * Math.cos(theta));
	}

	/**
	 * Calculating the y value of a point on a 2D circle in a specific sphere
	 * @param rPhi The radius of the circle at a specific latitude in the sphere
	 * @param theta The longitude angle of current point
	 * @return	the y value of a point on a 2D circle
	 */
	private float calcuY(float rPhi, float theta){
		return (float) (rPhi * Math.sin(theta));
	}

}

 