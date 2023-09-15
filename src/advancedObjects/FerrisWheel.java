package advancedObjects;

import GraphicsObjects.Utils;
import objects3D.*;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class FerrisWheel {

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
     * define the texture of each part of this FerrisWheel
     **/
    private Texture textureRoom;    // each room

    //constructor
    public FerrisWheel(Texture textureRoom){
        this.textureRoom = textureRoom;
    }

    /**
     * For drawing a ferris wheel
     * @param delta
     */
    public void draw(float delta, boolean isStart, float speed){
        /*
            theta keep growing.
            cos(theta) swings between -1, 1
         */
        float theta = (float) (delta * 2 * Math.PI);

        //to control whether start or not this ferris is
        if (!isStart){
            theta = 0;
        }

        /** init the rotation coefficients */
        /*
            to keep the room perpendicular to the ground, the roomRodRotation should have the same
            angular velocity with rodRotation, but in the inverse direction
         */
        float rodRotation = theta * speed;
        float roomRodRotation = theta * speed;
        //for test
//        rodRotation = 0;
//        roomRodRotation = 0;


        //init the basic 3D objects
        Sphere sphere= new Sphere();
        Cylinder cylinder= new Cylinder();
        Cube cube = new Cube();
        TexCube texCube = new TexCube();

        //start to draw the ferris wheel
        GL11.glPushMatrix();{

            //front left pedestal
            GL11.glTranslatef(0.0f,17.0f,0.0f);
            GL11.glRotatef(110, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(15, 0.0f, -1.0f, 0.0f);
            cylinder.DrawCylinder(0.4f, 40f, 32);

            //front right pedestal
            GL11.glPushMatrix();{
                GL11.glTranslatef(0.0f,0.0f,0.0f);
                GL11.glRotatef(38.9f, 0.0f, 1.0f, 0.0f);
                cylinder.DrawCylinder(0.4f, 40f, 32);


                //back right pedestal
                GL11.glPushMatrix();{
                    GL11.glTranslatef(0.0f,0.0f,0.0f);
                    GL11.glRotatef(30, -1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(5, 0.0f, 1.0f, 0.0f);
                    cylinder.DrawCylinder(0.4f, 40f, 32);


                    //back left pedestal
                    GL11.glPushMatrix();{
                        GL11.glTranslatef(0.0f,0.0f,0.0f);
                        GL11.glRotatef(40, 0.0f, -1.0f, 0.0f);
                        GL11.glRotatef(10, -1.0f, 0.0f, 0.0f);
                        cylinder.DrawCylinder(0.4f, 40f, 32);

                        //rod no.1
                        GL11.glColor3f(green[0], green[1], green[2]);
                        GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(green));
                        GL11.glPushMatrix();{
                            GL11.glTranslatef(0.0f,0.0f,0.0f);
                            GL11.glRotatef(160, 0.0f, -1.0f, 0.0f);
                            GL11.glRotatef(20, -1.0f, 0.0f, 0.0f);
                            //keep anticlockwise rotating
                            /*
                                NOTICE: all the rods are combined, which means each one is generated according to the one before it.
                                So, we should just only let the first one keep rotating, then all the other rods will keep the relative position
                             */
                            GL11.glRotatef(rodRotation, 0.0f, 1.0f, 0.0f);
                            cylinder.DrawCylinder(0.2f, 20f, 32);

                            //extend rod no.1
                            GL11.glColor3f(blue[0], blue[1], blue[2]);
                            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
                            GL11.glPushMatrix();{
                                GL11.glTranslatef(0.0f,0.0f,20.0f);
                                GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                cylinder.DrawCylinder(0.1f, 3f, 32);

                                //room rod 1
                                GL11.glColor3f(red[0], red[1], red[2]);
                                GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(red));
                                GL11.glPushMatrix();{
                                    GL11.glTranslatef(0.0f,0.0f,3.0f);
                                    GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                    GL11.glRotatef(5, 0.0f, 1.0f, 0.0f);
                                    //keep rotating to be perpendicular to the ground
                                    GL11.glRotatef(roomRodRotation, 0.0f, 1.0f, 0.0f);
                                    cylinder.DrawCylinder(0.1f, 3f, 32);

//                                    //room 1
//                                    GL11.glColor3f(blue[0], blue[1], blue[2]);
//                                    GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
//                                    GL11.glPushMatrix();{
//                                        GL11.glTranslatef(0.0f,0.0f,3.0f);
//                                        GL11.glScalef(2, 2, 2);
//                                        cube.DrawCube();
//
//                                        GL11.glPopMatrix();
//                                    }

                                    /** draw room 1 using a textured cube */
                                    GL11.glPushMatrix();{
                                        GL11.glTranslatef(0.0f,0.0f,3.0f);
                                        GL11.glScalef(2, 2, 2);

                                        GL11.glTexParameteri(
                                                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                                GL11.GL_CLAMP);

                                        Color.white.bind();
                                        textureRoom.bind();
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

                            //rod no.2
                            GL11.glColor3f(green[0], green[1], green[2]);
                            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(green));
                            GL11.glPushMatrix();{
                                GL11.glTranslatef(0.0f,0.0f,0.0f);
                                GL11.glRotatef(45, 0.0f, 1.0f, 0.0f);
                                cylinder.DrawCylinder(0.2f, 20f, 32);

                                //extend rod no.2
                                GL11.glColor3f(blue[0], blue[1], blue[2]);
                                GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
                                GL11.glPushMatrix();{
                                    GL11.glTranslatef(0.0f,0.0f,20.0f);
                                    GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                    cylinder.DrawCylinder(0.1f, 3f, 32);

                                    //room rod 2
                                    GL11.glColor3f(red[0], red[1], red[2]);
                                    GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(red));
                                    GL11.glPushMatrix();{
                                        GL11.glTranslatef(0.0f,0.0f,3.0f);
                                        GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                        GL11.glRotatef(50, 0.0f, 1.0f, 0.0f);
                                        //keep rotating to be perpendicular to the ground
                                        GL11.glRotatef(roomRodRotation, 0.0f, 1.0f, 0.0f);
                                        cylinder.DrawCylinder(0.1f, 3f, 32);

                                        //room 2
//                                        GL11.glColor3f(blue[0], blue[1], blue[2]);
//                                        GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
//                                        GL11.glPushMatrix();{
//                                            GL11.glTranslatef(0.0f,0.0f,3.0f);
//                                            GL11.glScalef(2, 2, 2);
//                                            cube.DrawCube();
//
//                                            GL11.glPopMatrix();
//                                        }

                                        /** draw room 2 using a textured cube */
                                        GL11.glPushMatrix();{
                                            GL11.glTranslatef(0.0f,0.0f,3.0f);
                                            GL11.glScalef(2, 2, 2);

                                            GL11.glTexParameteri(
                                                    GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                                    GL11.GL_CLAMP);

                                            Color.white.bind();
                                            textureRoom.bind();
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

                                //rod no.3
                                GL11.glColor3f(green[0], green[1], green[2]);
                                GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(green));
                                GL11.glPushMatrix();{
                                    GL11.glTranslatef(0.0f,0.0f,0.0f);
                                    GL11.glRotatef(45, 0.0f, 1.0f, 0.0f);
                                    cylinder.DrawCylinder(0.2f, 20f, 32);

                                    //extend rod no.3
                                    GL11.glColor3f(blue[0], blue[1], blue[2]);
                                    GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
                                    GL11.glPushMatrix();{
                                        GL11.glTranslatef(0.0f,0.0f,20.0f);
                                        GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                        cylinder.DrawCylinder(0.1f, 3f, 32);

                                        //room rod 3
                                        GL11.glColor3f(red[0], red[1], red[2]);
                                        GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(red));
                                        GL11.glPushMatrix();{
                                            GL11.glTranslatef(0.0f,0.0f,3.0f);
                                            GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                            GL11.glRotatef(95, 0.0f, 1.0f, 0.0f);
                                            //keep rotating to be perpendicular to the ground
                                            GL11.glRotatef(roomRodRotation, 0.0f, 1.0f, 0.0f);
                                            cylinder.DrawCylinder(0.1f, 3f, 32);

                                            //room 3
//                                            GL11.glColor3f(blue[0], blue[1], blue[2]);
//                                            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
//                                            GL11.glPushMatrix();{
//                                                GL11.glTranslatef(0.0f,0.0f,3.0f);
//                                                GL11.glScalef(2, 2, 2);
//                                                cube.DrawCube();
//
//                                                GL11.glPopMatrix();
//                                            }

                                            /** draw room 3 using a textured cube */
                                            GL11.glPushMatrix();{
                                                GL11.glTranslatef(0.0f,0.0f,3.0f);
                                                GL11.glScalef(2, 2, 2);

                                                GL11.glTexParameteri(
                                                        GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                                        GL11.GL_CLAMP);

                                                Color.white.bind();
                                                textureRoom.bind();
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

                                    //rod no.4
                                    GL11.glColor3f(green[0], green[1], green[2]);
                                    GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(green));
                                    GL11.glPushMatrix();{
                                        GL11.glTranslatef(0.0f,0.0f,0.0f);
                                        GL11.glRotatef(45, 0.0f, 1.0f, 0.0f);
                                        cylinder.DrawCylinder(0.2f, 20f, 32);

                                        //extend rod no.4
                                        GL11.glColor3f(blue[0], blue[1], blue[2]);
                                        GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
                                        GL11.glPushMatrix();{
                                            GL11.glTranslatef(0.0f,0.0f,20.0f);
                                            GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                            cylinder.DrawCylinder(0.1f, 3f, 32);

                                            //room rod 4
                                            GL11.glColor3f(red[0], red[1], red[2]);
                                            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(red));
                                            GL11.glPushMatrix();{
                                                GL11.glTranslatef(0.0f,0.0f,3.0f);
                                                GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                                GL11.glRotatef(140, 0.0f, 1.0f, 0.0f);
                                                //keep rotating to be perpendicular to the ground
                                                GL11.glRotatef(roomRodRotation, 0.0f, 1.0f, 0.0f);
                                                cylinder.DrawCylinder(0.1f, 3f, 32);

                                                //room 4
//                                                GL11.glColor3f(blue[0], blue[1], blue[2]);
//                                                GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
//                                                GL11.glPushMatrix();{
//                                                    GL11.glTranslatef(0.0f,0.0f,3.0f);
//                                                    GL11.glScalef(2, 2, 2);
//                                                    cube.DrawCube();
//
//                                                    GL11.glPopMatrix();
//                                                }

                                                /** draw room 4 using a textured cube */
                                                GL11.glPushMatrix();{
                                                    GL11.glTranslatef(0.0f,0.0f,3.0f);
                                                    GL11.glScalef(2, 2, 2);

                                                    GL11.glTexParameteri(
                                                            GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                                            GL11.GL_CLAMP);

                                                    Color.white.bind();
                                                    textureRoom.bind();
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

                                        //rod no.5
                                        GL11.glColor3f(green[0], green[1], green[2]);
                                        GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(green));
                                        GL11.glPushMatrix();{
                                            GL11.glTranslatef(0.0f,0.0f,0.0f);
                                            GL11.glRotatef(45, 0.0f, 1.0f, 0.0f);
                                            cylinder.DrawCylinder(0.2f, 20f, 32);

                                            //extend rod no.5
                                            GL11.glColor3f(blue[0], blue[1], blue[2]);
                                            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
                                            GL11.glPushMatrix();{
                                                GL11.glTranslatef(0.0f,0.0f,20.0f);
                                                GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                                cylinder.DrawCylinder(0.1f, 3f, 32);

                                                //room rod 5
                                                GL11.glColor3f(red[0], red[1], red[2]);
                                                GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(red));
                                                GL11.glPushMatrix();{
                                                    GL11.glTranslatef(0.0f,0.0f,3.0f);
                                                    GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                                    GL11.glRotatef(185, 0.0f, 1.0f, 0.0f);
                                                    //keep rotating to be perpendicular to the ground
                                                    GL11.glRotatef(roomRodRotation, 0.0f, 1.0f, 0.0f);
                                                    cylinder.DrawCylinder(0.1f, 3f, 32);

                                                    //room 5
//                                                    GL11.glColor3f(blue[0], blue[1], blue[2]);
//                                                    GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
//                                                    GL11.glPushMatrix();{
//                                                        GL11.glTranslatef(0.0f,0.0f,3.0f);
//                                                        GL11.glScalef(2, 2, 2);
//                                                        cube.DrawCube();
//
//                                                        GL11.glPopMatrix();
//                                                    }

                                                    /** draw room 5 using a textured cube */
                                                    GL11.glPushMatrix();{
                                                        GL11.glTranslatef(0.0f,0.0f,3.0f);
                                                        GL11.glScalef(2, 2, 2);

                                                        GL11.glTexParameteri(
                                                                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                                                GL11.GL_CLAMP);

                                                        Color.white.bind();
                                                        textureRoom.bind();
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

                                            //rod no.6
                                            GL11.glColor3f(green[0], green[1], green[2]);
                                            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(green));
                                            GL11.glPushMatrix();{
                                                GL11.glTranslatef(0.0f,0.0f,0.0f);
                                                GL11.glRotatef(45, 0.0f, 1.0f, 0.0f);
                                                cylinder.DrawCylinder(0.2f, 20f, 32);

                                                //extend rod no.6
                                                GL11.glColor3f(blue[0], blue[1], blue[2]);
                                                GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
                                                GL11.glPushMatrix();{
                                                    GL11.glTranslatef(0.0f,0.0f,20.0f);
                                                    GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                                    cylinder.DrawCylinder(0.1f, 3f, 32);

                                                    //room rod 6
                                                    GL11.glColor3f(red[0], red[1], red[2]);
                                                    GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(red));
                                                    GL11.glPushMatrix();{
                                                        GL11.glTranslatef(0.0f,0.0f,3.0f);
                                                        GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                                        GL11.glRotatef(230, 0.0f, 1.0f, 0.0f);
                                                        //keep rotating to be perpendicular to the ground
                                                        GL11.glRotatef(roomRodRotation, 0.0f, 1.0f, 0.0f);
                                                        cylinder.DrawCylinder(0.1f, 3f, 32);

                                                        //room 6
//                                                        GL11.glColor3f(blue[0], blue[1], blue[2]);
//                                                        GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
//                                                        GL11.glPushMatrix();{
//                                                            GL11.glTranslatef(0.0f,0.0f,3.0f);
//                                                            GL11.glScalef(2, 2, 2);
//                                                            cube.DrawCube();
//
//                                                            GL11.glPopMatrix();
//                                                        }

                                                        /** draw room 6 using a textured cube */
                                                        GL11.glPushMatrix();{
                                                            GL11.glTranslatef(0.0f,0.0f,3.0f);
                                                            GL11.glScalef(2, 2, 2);

                                                            GL11.glTexParameteri(
                                                                    GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                                                    GL11.GL_CLAMP);

                                                            Color.white.bind();
                                                            textureRoom.bind();
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

                                                //rod no.7
                                                GL11.glColor3f(green[0], green[1], green[2]);
                                                GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(green));
                                                GL11.glPushMatrix();{
                                                    GL11.glTranslatef(0.0f,0.0f,0.0f);
                                                    GL11.glRotatef(45, 0.0f, 1.0f, 0.0f);
                                                    cylinder.DrawCylinder(0.2f, 20f, 32);

                                                    //extend rod no.7
                                                    GL11.glColor3f(blue[0], blue[1], blue[2]);
                                                    GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
                                                    GL11.glPushMatrix();{
                                                        GL11.glTranslatef(0.0f,0.0f,20.0f);
                                                        GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                                        cylinder.DrawCylinder(0.1f, 3f, 32);

                                                        //room rod 7
                                                        GL11.glColor3f(red[0], red[1], red[2]);
                                                        GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(red));
                                                        GL11.glPushMatrix();{
                                                            GL11.glTranslatef(0.0f,0.0f,3.0f);
                                                            GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                                            GL11.glRotatef(275, 0.0f, 1.0f, 0.0f);
                                                            //keep rotating to be perpendicular to the ground
                                                            GL11.glRotatef(roomRodRotation, 0.0f, 1.0f, 0.0f);
                                                            cylinder.DrawCylinder(0.1f, 3f, 32);

                                                            //room 7
//                                                            GL11.glColor3f(blue[0], blue[1], blue[2]);
//                                                            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
//                                                            GL11.glPushMatrix();{
//                                                                GL11.glTranslatef(0.0f,0.0f,3.0f);
//                                                                GL11.glScalef(2, 2, 2);
//                                                                cube.DrawCube();
//
//                                                                GL11.glPopMatrix();
//                                                            }

                                                            /** draw room 7 using a textured cube */
                                                            GL11.glPushMatrix();{
                                                                GL11.glTranslatef(0.0f,0.0f,3.0f);
                                                                GL11.glScalef(2, 2, 2);

                                                                GL11.glTexParameteri(
                                                                        GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                                                        GL11.GL_CLAMP);

                                                                Color.white.bind();
                                                                textureRoom.bind();
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

                                                    //rod no.8
                                                    GL11.glColor3f(green[0], green[1], green[2]);
                                                    GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(green));
                                                    GL11.glPushMatrix();{
                                                        GL11.glTranslatef(0.0f,0.0f,0.0f);
                                                        GL11.glRotatef(45, 0.0f, 1.0f, 0.0f);
                                                        cylinder.DrawCylinder(0.2f, 20f, 32);

                                                        //extend rod no.8
                                                        GL11.glColor3f(blue[0], blue[1], blue[2]);
                                                        GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
                                                        GL11.glPushMatrix();{
                                                            GL11.glTranslatef(0.0f,0.0f,20.0f);
                                                            GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                                            cylinder.DrawCylinder(0.1f, 3f, 32);

                                                            //room rod 8
                                                            GL11.glColor3f(red[0], red[1], red[2]);
                                                            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(red));
                                                            GL11.glPushMatrix();{
                                                                GL11.glTranslatef(0.0f,0.0f,3.0f);
                                                                GL11.glRotatef(90, 1.0f, 0.0f, 0.0f);
                                                                GL11.glRotatef(320, 0.0f, 1.0f, 0.0f);
                                                                //keep rotating to be perpendicular to the ground
                                                                GL11.glRotatef(roomRodRotation, 0.0f, 1.0f, 0.0f);
                                                                cylinder.DrawCylinder(0.1f, 3f, 32);

                                                                //room 8
//                                                                GL11.glColor3f(blue[0], blue[1], blue[2]);
//                                                                GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
//                                                                GL11.glPushMatrix();{
//                                                                    GL11.glTranslatef(0.0f,0.0f,3.0f);
//                                                                    GL11.glScalef(2, 2, 2);
//                                                                    cube.DrawCube();
//
//                                                                    GL11.glPopMatrix();
//                                                                }

                                                                /** draw room 8 using a textured cube */
                                                                GL11.glPushMatrix();{
                                                                    GL11.glTranslatef(0.0f,0.0f,3.0f);
                                                                    GL11.glScalef(2, 2, 2);

                                                                    GL11.glTexParameteri(
                                                                            GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                                                                            GL11.GL_CLAMP);

                                                                    Color.white.bind();
                                                                    textureRoom.bind();
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
                                                    GL11.glPopMatrix();
                                                }
                                                GL11.glPopMatrix();
                                            }
                                            GL11.glPopMatrix();
                                        }
                                        GL11.glPopMatrix();
                                    }
                                    GL11.glPopMatrix();
                                }
                                GL11.glPopMatrix();
                            }
                            GL11.glPopMatrix();
                        }
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
