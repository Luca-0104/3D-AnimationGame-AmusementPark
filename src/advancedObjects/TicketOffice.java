package advancedObjects;

import GraphicsObjects.Utils;
import objects3D.Cube;
import objects3D.Cylinder;
import objects3D.Sphere;
import objects3D.TexCube;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class TicketOffice {

    // basic colours
    static float black[] = {0.0f, 0.0f, 0.0f, 1.0f};
    static float white[] = {1.0f, 1.0f, 1.0f, 1.0f};

    static float grey[] = {0.5f, 0.5f, 0.5f, 1.0f};
    static float spot[] = {0.1f, 0.1f, 0.1f, 0.5f};

    // primary colours
    static float red[] = {1.0f, 0.0f, 0.0f, 1.0f};
    static float green[] = {0.0f, 1.0f, 0.0f, 1.0f};
    static float blue[] = {0.0f, 0.0f, 1.0f, 1.0f};

    // secondary colours
    static float yellow[] = {1.0f, 1.0f, 0.0f, 1.0f};
    static float magenta[] = {1.0f, 0.0f, 1.0f, 1.0f};
    static float cyan[] = {0.0f, 1.0f, 1.0f, 1.0f};

    // other colours
    static float orange[] = {1.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float brown[] = {0.5f, 0.25f, 0.0f, 1.0f, 1.0f};
    static float dkgreen[] = {0.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float pink[] = {1.0f, 0.6f, 0.6f, 1.0f, 1.0f};

    static float liteRed[] = {220f/255, 0.0f, 242f/255, 1.0f, 1.0f};


    /**
     * define the texture of each part of this ticket office
     **/
    private Texture textureSign;    // a ticket office sign
    private Texture textureBody;    // building body
    private Texture textureShadow;    // building body

    public TicketOffice(Texture textureSign, Texture textureBody, Texture textureShadow) {
        this.textureSign = textureSign;
        this.textureBody = textureBody;
        this.textureShadow = textureShadow;
    }

    /**
     * For drawing a ticket office
     *
     * @param delta
     */
    public void draw(float delta) {
        //init the basic 3D objects
        Cube cube = new Cube();
        Roof roof = new Roof();
        Cylinder cylinder = new Cylinder();
        TexCube texCube = new TexCube();

        //start to draw ticket office
        GL11.glPushMatrix();
        {

            //the building body
            /** draw the building body of ticket office using a textured cube */
            GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(15f, 10f, 10f);
            GL11.glTexParameteri(
                    GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                    GL11.GL_CLAMP);

            Color.white.bind();
            textureBody.bind();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

            texCube.DrawTexCube();

//            //shadow of the house
//            /** draw the shadow using a squeezed cube */
//            GL11.glPushMatrix();{
//                GL11.glTranslatef(-5, -0.9f, 0f);
//                GL11.glRotatef(45, 0.0f, 1.0f, 0.0f);
//                GL11.glScalef(5f, 0f, 3f);
//                GL11.glTexParameteri(
//                        GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
//                        GL11.GL_CLAMP);
//                Color.white.bind();
//                textureShadow.bind();
//                GL11.glEnable(GL11.GL_TEXTURE_2D);
//                GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
//                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
//                GL11.glEnable(GL11.GL_BLEND);
//                texCube.DrawTexCube();
//
//                GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
//                GL11.glPopMatrix();
//            }

            // roof
//            GL11.glColor3f(22f/255, 0f/255, 24f/255);
            GL11.glColor3f(liteRed[0], liteRed[1], liteRed[2]);
            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(liteRed));
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(0.0f, 0.5f, 0.0f);
                GL11.glRotatef(90f, -1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45f, 0.0f, 0.0f, 1.0f);
                GL11.glScalef(2f, 2f, 2f);
                roof.draw();

                //sign rog
                GL11.glColor3f(pink[0], pink[1], pink[2]);
                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(pink));
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0.3f, -0.2f, 0.3f);
                    cylinder.DrawCylinder(0.02f, 0.8f, 32);

                    /** draw the sign of ticket office using a squeezed textured cube */
                    //the sign
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 1.3f);
                        GL11.glRotatef(-15, 0.0f, 0.0f, 1.0f);
                        GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                        // make the cube into a sign (2D surface)
                        GL11.glScalef(0f, .5f, .7f);

                        GL11.glTexParameteri(
                                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                GL11.GL_CLAMP);

                        Color.white.bind();
                        textureSign.bind();
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
