package advancedObjects;

import GraphicsObjects.Utils;
import objects3D.Cylinder;
import objects3D.TexCube;
import objects3D.TexSphere;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class Tree {

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
    private Texture textureTreeTop;
    private Texture textureShadow;

    /**
     * constructor
     */
    public Tree(Texture textureTreeTop, Texture textureShadow){
        this.textureTreeTop = textureTreeTop;
        this.textureShadow = textureShadow;
    }

    /**
     * For drawing a tree
     */
    public void draw(){

        //init the basic 3D objects
        Cylinder cylinder= new Cylinder();
        TexSphere texSphere = new TexSphere();
        TexCube texCube = new TexCube();

        //start to draw a tree
        GL11.glPushMatrix();{

            //the trunk
            GL11.glColor3f(237f/255, 47f/255, 47f/255);
            GL11.glTranslatef(20, 40, 0);
            GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
            cylinder.DrawCylinder(1, 17, 32);

            //the tree top
            /** draw the tree top using a textured sphere */
            GL11.glPushMatrix();{
                GL11.glScalef(1, 1,.7f);
                GL11.glTexParameteri(
                        GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                        GL11.GL_CLAMP);
                Color.white.bind();
                textureTreeTop.bind();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

//                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
//                GL11.glEnable(GL11.GL_BLEND);

                texSphere.DrawTexSphere(8, 32, 32, textureTreeTop);


                //shadow of the tree
                /** draw the shadow using a squeezed cube */
                GL11.glPushMatrix();{
                    GL11.glTranslatef(10, -1.7f, 23.1f);
                    GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                    GL11.glScalef(10f, 0f, 5f);
                    GL11.glTexParameteri(
                            GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
                            GL11.GL_CLAMP);
                    Color.white.bind();
                    textureShadow.bind();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
                    GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
                    GL11.glEnable(GL11.GL_BLEND);
                    texCube.DrawTexCube();

                    GL11.glPopMatrix();
                }

                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }

    }
}
