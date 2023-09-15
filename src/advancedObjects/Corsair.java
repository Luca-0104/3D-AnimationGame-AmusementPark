package advancedObjects;

import GraphicsObjects.Utils;
import objects3D.Cube;
import objects3D.Cylinder;
import objects3D.Sphere;
import objects3D.TexCube;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import java.time.Year;

public class Corsair {

    // basic colours
    static float black[] = { 0.0f, 0.0f, 0.0f, 1.0f };
    static float white[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    static float grey[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    static float spot[] = { 0.1f, 0.1f, 0.1f, 0.5f };

    // primary colours
    static float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };
    static float green[] = { 0.0f, 1.0f, 0.0f, 1.0f };
    static float blue[] = { 0.0f, 0.0f, 1.0f, 1.0f };

    // secondary colours
    static float yellow[] = { 1.0f, 1.0f, 0.0f, 1.0f };
    static float magenta[] = { 1.0f, 0.0f, 1.0f, 1.0f };
    static float cyan[] = { 0.0f, 1.0f, 1.0f, 1.0f };

    // other colours
    static float orange[] = { 1.0f, 0.5f, 0.0f, 1.0f, 1.0f };
    static float brown[] = { 0.5f, 0.25f, 0.0f, 1.0f, 1.0f };
    static float dkgreen[] = { 0.0f, 0.5f, 0.0f, 1.0f, 1.0f };
    static float pink[] = { 1.0f, 0.6f, 0.6f, 1.0f, 1.0f };

    /**
     * define the texture of each part of this Corsair
     **/
    private Texture textureCorsair;    // each room

    //constructor
    public Corsair(Texture textureCorsair){
        this.textureCorsair = textureCorsair;
    }

    /**
     * For drawing a ferris wheel
     * @param delta
     */
    public void draw(float delta, boolean isCorsairStart){
        /*
            theta keep growing.
            cos(theta) swings between -1, 1
         */
        float theta = (float) (delta * 2 * Math.PI);

        //to control whether start or not this corsair is
        if(!isCorsairStart){
            theta = 0;
        }

        /** init the rotation coefficients */
        float corsairRotation = (float) (Math.cos(theta) * 500);
        //for test
//        corsairRotation = 0;

        //init the basic 3D objects
        Cylinder cylinder= new Cylinder();
        Cube cube = new Cube();
        TexCube texCube = new TexCube();


        //start to draw corsair
        GL11.glPushMatrix();{

            //horizontal connecting rod
            cylinder.DrawCylinder(0.4f, 10f, 32);

            /*
                NOTICE: The code blocks of the pedestals are in series, not nested!
             */

            //front left pedestal
            GL11.glColor3f(blue[0], blue[1], blue[2]);
            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
            GL11.glPushMatrix();{
                GL11.glTranslatef(0.0f,0.0f,0.0f);
                GL11.glRotatef(90f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(35f, 0.0f, -1.0f, 0.0f);
                GL11.glRotatef(15f, 1.0f, 0.0f, 0.0f);

                cylinder.DrawCylinder(0.4f, 40f, 32);

                GL11.glPopMatrix();
            }

            //front right pedestal
            GL11.glColor3f(blue[0], blue[1], blue[2]);
            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
            GL11.glPushMatrix();{
                GL11.glTranslatef(0.0f,0.0f,0.0f);
                GL11.glRotatef(90f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(35f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(15f, 1.0f, 0.0f, 0.0f);

                cylinder.DrawCylinder(0.4f, 40f, 32);

                GL11.glPopMatrix();
            }

            //back left pedestal
            GL11.glColor3f(blue[0], blue[1], blue[2]);
            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
            GL11.glPushMatrix();{
                GL11.glTranslatef(0.0f,0.0f,10.0f);
                GL11.glRotatef(90f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(35f, 0.0f, -1.0f, 0.0f);
                GL11.glRotatef(15f, -1.0f, 0.0f, 0.0f);

                cylinder.DrawCylinder(0.4f, 40f, 32);

                GL11.glPopMatrix();
            }

            //back right pedestal
            GL11.glColor3f(green[0], green[1], green[2]);
            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(green));
            GL11.glPushMatrix();{
                GL11.glTranslatef(0.0f,0.0f,10.0f);
                GL11.glRotatef(90f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(35f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(15f, -1.0f, 0.0f, 0.0f);

                cylinder.DrawCylinder(0.4f, 40f, 32);

                GL11.glPopMatrix();
            }


            /* here, we are still at the stack of "horizontal connecting rod" */


            /*
                NOTICE: we should let the left rod, right rod and boat be combined,
                therefore, their code blocks should be nested
             */

            //left rod
            GL11.glColor3f(blue[0], blue[1], blue[2]);
            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
            GL11.glPushMatrix();{
                GL11.glTranslatef(0.0f,0.0f,5.0f);
                GL11.glRotatef(90f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(35f, 0.0f, -1.0f, 0.0f);
                //let the corsair keep swinging
                GL11.glRotatef(corsairRotation, 0.0f, 1.0f, 0.0f);

                cylinder.DrawCylinder(0.4f, 20f, 32);

                //right rod
                GL11.glColor3f(blue[0], blue[1], blue[2]);
                GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
                GL11.glPushMatrix();{
                    GL11.glTranslatef(0.0f,0.0f,0.0f);
                    GL11.glRotatef(70f, 0.0f, 1.0f, 0.0f);

                    cylinder.DrawCylinder(0.4f, 20f, 32);

                    //Corsair boat
//                    GL11.glColor3f(red[0], red[1], red[2]);
//                    GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(red));
//                    GL11.glPushMatrix();{
//                        GL11.glTranslatef(-11.0f,0.0f,16.0f);
//                        GL11.glRotatef(55f, 0.0f, 1.0f, 0.0f);
//                        GL11.glScalef(4, 4,16);
//
//                        cube.DrawCube();
//
//                        GL11.glPopMatrix();
//                    }

                    /** draw the Corsair boat using a textured cube */
                    GL11.glPushMatrix();{
                        GL11.glTranslatef(-11.0f,0.0f,16.0f);
                        GL11.glRotatef(55f, 0.0f, 1.0f, 0.0f);
                        GL11.glScalef(4, 4,16);
                        GL11.glTexParameteri(
                                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                GL11.GL_CLAMP);
                        Color.white.bind();
                        textureCorsair.bind();
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                        texCube.DrawTexCube();

                        GL11.glPopMatrix();
                    }




                    GL11.glPopMatrix();
                }
                GL11.glPopMatrix();
            }











            GL11.glPopMatrix();
        }

    }

}
