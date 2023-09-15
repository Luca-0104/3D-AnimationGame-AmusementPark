package objects3D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;

public class TexCube {

    // an array of vertices on the cube
    private Point4f[] vertices;
    // an array of several groups of indices of the vertices in the vertices array, each group consists a face of the cube
    private int[][] faces;

    public TexCube() {
        // initialize the vertices in the cube
        this.vertices = new Point4f[]{
                new Point4f(-1.0f, -1.0f, -1.0f, 0.0f),
                new Point4f(-1.0f, -1.0f, 1.0f, 0.0f),
                new Point4f(-1.0f, 1.0f, -1.0f, 0.0f),
                new Point4f(-1.0f, 1.0f, 1.0f, 0.0f),
                new Point4f(1.0f, -1.0f, -1.0f, 0.0f),
                new Point4f(1.0f, -1.0f, 1.0f, 0.0f),
                new Point4f(1.0f, 1.0f, -1.0f, 0.0f),
                new Point4f(1.0f, 1.0f, 1.0f, 0.0f)
        };

        // initialize the faces of the cube
        this.faces = new int[][]{
                {0, 4, 5, 1},   //bottom
                {0, 2, 6, 4},   //front
                {0, 1, 3, 2},   //left
                {4, 6, 7, 5},   //right
                {1, 5, 7, 3},   //back
                {2, 3, 7, 6}    //top
        };
    }

    // Implement using notes  and looking at TexSphere
    public void DrawTexCube() {

        GL11.glBegin(GL11.GL_QUADS);

        //loop through the faces of a cube
        for (int face = 0; face < faces.length; face++){
            // using the vertices on this face to get two different vectors on this face, which are used to calculate the normal vector of this face
            Vector4f v = vertices[faces[face][1]].MinusPoint(vertices[faces[face][0]]);
            Vector4f w = vertices[faces[face][3]].MinusPoint(vertices[faces[face][0]]);

            // calculate the normal vector this face
            Vector4f normal = v.cross(w).Normal();
            GL11.glNormal3f(normal.x, normal.y, normal.z);


            /* point 1 on this face */
            GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);
            GL11.glTexCoord2f(0, 1);    // set the texture coordinates

            /* point 2 on this face */
            GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);
            GL11.glTexCoord2f(0, 0);    // set the texture coordinates

            /* point 3 on this face */
            GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);
            GL11.glTexCoord2f(1, 0);    // set the texture coordinates

            /* point 4 on this face */
            GL11.glVertex3f(vertices[faces[face][3]].x, vertices[faces[face][3]].y, vertices[faces[face][3]].z);
            GL11.glTexCoord2f(1, 1);    // set the texture coordinates


        }


        GL11.glEnd();
    }


}
 
	/*
	 
	 
}

	*/