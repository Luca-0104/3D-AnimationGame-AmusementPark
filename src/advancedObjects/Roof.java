package advancedObjects;

import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;

public class Roof {

    private Point4f[] vertices;	//an array of vertices in this roof
    private int[][] triangles;	//each inner array contains 3 int representing the index of the vertex in the vertices array, which are forming a triangle

    public Roof(){
        //initialize the vertices, putting the points in the roof into the array
        this.vertices = new Point4f[]{
                new Point4f(0.0f, 0.0f, 1.0f, 0.0f),	//0
                new Point4f(1.0f, 0.0f, 0.0f, 0.0f),	//1
                new Point4f(0.0f, 1.0f, 0.0f, 0.0f),	//2
                new Point4f(0.0f, -1.0f, 0.0f, 0.0f),	//3
                new Point4f(0.0f, 0.0f, -1.0f, 0.0f),	//4
                new Point4f(-1.0f, 0.0f, 0.0f, 0.0f)	//5
        };

        //initialize the triangles in the roof, each face formed by 3 points.
        //each inner array contains 3 int representing the index of the vertex in the vertices array, which are forming a triangle
        this.triangles = new int[][]{
                {0, 1, 2},	//face 0
                {0, 2, 5},	//face 1
                {0, 3, 5},	//face 2
                {0, 1, 3},	//face 3
        };
    }


    public void draw() {

        GL11.glBegin(GL11.GL_TRIANGLES);

        //loop through the triangle faces in the octahedron
        for (int triangle = 0; triangle < triangles.length; triangle++) { // per triangle
            //vector v and w are two boundary vectors of the triangle, which start at the same vertex
            // v(0, 1) w(2, 0)
            // (numbers represent the index of the vertex in the vertices array)
            Vector4f v = vertices[triangles[triangle][1]].MinusPoint(vertices[triangles[triangle][0]]);
            Vector4f w = vertices[triangles[triangle][2]].MinusPoint(vertices[triangles[triangle][0]]);

            //calculate the normalized normal vector of this triangle face by using the cross product
            //because cross product can give us a vector perpendicular to the face
            //this can make the graphic smoother
            Vector4f normal = v.cross(w).Normal();

            //set the normal vector in OpenGL
            GL11.glNormal3f(normal.x, normal.y, normal.z);

            //set the 3 vertices of this triangle in OpenGl
            GL11.glVertex3f(vertices[triangles[triangle][0]].x, vertices[triangles[triangle][0]].y, vertices[triangles[triangle][0]].z);
            GL11.glVertex3f(vertices[triangles[triangle][1]].x, vertices[triangles[triangle][1]].y, vertices[triangles[triangle][1]].z);
            GL11.glVertex3f(vertices[triangles[triangle][2]].x, vertices[triangles[triangle][2]].y, vertices[triangles[triangle][2]].z);

        } // per triangle

        GL11.glEnd();
    }

}
