
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import advancedObjects.*;
import objects3D.TexCube;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import GraphicsObjects.Arcball;
import GraphicsObjects.Utils;
import objects3D.Grid;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

//Main windows class controls and creates the 3D virtual world , please do not change this class but edit the other classes to complete the assignment. 
// Main window is built upon the standard Helloworld LWJGL class which I have heavily modified to use as your standard openGL environment. 
// 

// Do not touch this class, I will be making a version of it for your 3rd Assignment 
public class MainWindow {

    private boolean MouseOnepressed = true;
    private boolean dragMode = false;
    private boolean BadAnimation = true;
    private boolean Earth = false;
    /**
     * position of pointer
     */
    float x = 400, y = 300;
    /**
     * angle of rotation
     */
    float rotation = 0;
    /**
     * time at last frame
     */
    long lastFrame;
    /**
     * frames per second
     */
    int fps;
    /**
     * last fps time
     */
    long lastFPS;

    long myDelta = 0; //to use for animation
    float Alpha = 0; //to use for animation
    long StartTime; // beginAnimiation

    Arcball MyArcball = new Arcball();

    boolean DRAWGRID = false;
    boolean waitForKeyrelease = true;
    /**
     * Mouse movement
     */
    int LastMouseX = -1;
    int LastMouseY = -1;

    float pullX = 0.0f; // arc ball  X cord.
    float pullY = 0.0f; // arc ball  Y cord.


    int OrthoNumber = 10000; // using this for screen size, making a window of 1200 x 800 so aspect ratio 3:2 // do not change this for assignment 3 but you can change everything for your project

    /**
     * this can justify the animating speed of human limbs
     **/
    float speed = 10.0f;
    /**
     * adjust the camera
     */
    float eyeX = 12.5f;
    float eyeY = -10f;
    float eyeZ = 50f;
    boolean isZoomedInOnce = false;
    /**
     * switch to control different objects
     */
    //elements in map are isFWinControl, isCorsairInControl, isHumanInControl respectively
    Map<String, Boolean> controlMap = new HashMap<>();
    private static final String MAP_KEY_FERRIS_WHEEL = "1";
    private static final String MAP_KEY_CORSAIR = "2";
    private static final String MAP_KEY_HUMAN = "3";
    /**
     * control the FerrisWheel
     */
    boolean isFerrisWheelStart = false;
    int speedFW = 50;
    /**
     * control the Corsair
     */
    boolean isCorsairStart = false;
    /**
     * control the Terrorist
     */
    boolean humanIsMoving = false;
    String humanOrientation = "w";
    int humanJumpHeight = 0;
    //offset of the initial location, we should use this to control the terrorist to move
    long terroristOffsetX = 0;
    long terroristOffsetY = 0;
    long terroristOffsetZ = 0;
    //the moving speed of the terrorist
    int speedTerrorist = 10;
    //the limb action speed of the terrorist
    float speedLimbTerrorist = 10.0f;
    //whether the terrorist is holding the bomb
    boolean isHoldingBomb = false;
    /**
     * control the bomb (the one on the ground)
     */
    boolean isInitBomb = true; // whether the bomb has never been picked up
    // if the terrorist drops the bomb, this should be the new location of the bomb
    float newBombX = 0;
    float newBombY = 0;
    float newBombZ = 0;
    boolean isExplode = false;  //whether the bomb is exploded
    boolean isPlanted = false;  //whether the bomb is planted (started)
    boolean isExploding = false;    //whether the exploding has been started (this is an "if" lock)
    long bombPlantedTime = 0;   //the record of the time that when the bomb is planted
    long bombDelay = 7000;   //the delay of the bomb to explode (5 sec)
    boolean isAudioPlay = false;    //whether the audio "bomb has been planted" is played
    /**
     * control the countdown-clock
     */
    //the location of the countdown clock
    long countdownX;
    long countdownY;
    long countdownZ;
    boolean isCountdownPositionRecorded = false;    //whether, the current location is recorded. (This is an "if" lock)
    /**
     * control the window trembling, window should tremble for a while after the bomb exploded
     */
    boolean isWindowTrembling = false;  //whether the window is trembling
    long winTremblingStartTime = 0;     //record the time that the window start to tremble
    long winTremblingDelay = 5000;      //how long the window will keep trembling (2 sec)
    boolean isFinalAudioPlay = false;





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

    // static GLfloat light_position[] = {0.0, 100.0, 100.0, 0.0};

    //support method to aid in converting a java float array into a Floatbuffer which is faster for the opengl layer to process


    /**
     * Initialize the controller map
     */
    private void initControls(){
        controlMap.put(MAP_KEY_FERRIS_WHEEL, false);
        controlMap.put(MAP_KEY_CORSAIR, false);
        controlMap.put(MAP_KEY_HUMAN, false);
    }


    public void start() {
        //initialize the controller map
        initControls();

        StartTime = getTime();
        try {
            Display.setDisplayMode(new DisplayMode(1200, 800));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        initGL(); // init OpenGL
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        while (!Display.isCloseRequested()) {
            int delta = getDelta();
            update(delta);
            renderGL();
            Display.update();
            Display.sync(120); // cap fps to 120fps
        }

        Display.destroy();
    }

    public void update(int delta) {
        // rotate quad
        //rotation += 0.01f * delta;


        int MouseX = Mouse.getX();
        int MouseY = Mouse.getY();
        int WheelPostion = Mouse.getDWheel();


        boolean MouseButonPressed = Mouse.isButtonDown(0);


        if (MouseButonPressed && !MouseOnepressed) {
            MouseOnepressed = true;
            //  System.out.println("Mouse drag mode");
            MyArcball.startBall(MouseX, MouseY, 1200, 800);
            dragMode = true;


        } else if (!MouseButonPressed) {
            // System.out.println("Mouse drag mode end ");
            MouseOnepressed = false;
            dragMode = false;
        }

        if (dragMode) {
            MyArcball.updateBall(MouseX, MouseY, 1200, 800);
        }

        if (WheelPostion > 0) {
            OrthoNumber += 300;

        }

        if (WheelPostion < 0) {
            OrthoNumber -= 300;
//            if (OrthoNumber < 610) {
//                OrthoNumber = 610;
//            }

            //  System.out.println("Orth nubmer = " +  OrthoNumber);

        }

        /** rest key is R */
        if (Keyboard.isKeyDown(Keyboard.KEY_R))
            MyArcball.reset();

        /* bad animation can be turn on or off using A key)*/

//        if (Keyboard.isKeyDown(Keyboard.KEY_A))
//            BadAnimation = !BadAnimation;
//        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
//            x += 0.35f * delta;
//        }

//        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
//            y += 0.35f * delta;
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
//            y -= 0.35f * delta;
//        }
//
//        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
//            rotation += 0.35f * delta;
//        }




        /*
         * set the keys to adjust the speed of action of the human body
         */
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            speed += 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            speed -= 0.01f;
        }

        /**
         * for choosing an object to control
         */
        if (Keyboard.isKeyDown(Keyboard.KEY_1)){
            //set all the control as false
            controlMap.replaceAll((s, v) -> false);
            //only start the control of the ferrisWheel
            controlMap.put(MAP_KEY_FERRIS_WHEEL, true);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_2)){
            //set all the control as false
            controlMap.replaceAll((s, v) -> false);
            //only start the control of the corsair
            controlMap.put(MAP_KEY_CORSAIR, true);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_3)){
            //set all the control as false
            controlMap.replaceAll((s, v) -> false);
            //only start the control of the human
            controlMap.put(MAP_KEY_HUMAN, true);
        }


        /*
        * keys to adjust the camera
        * */
        if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
            eyeX += 1.0f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
            eyeY += 0.6f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
            eyeX -= 1.0f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
            eyeY -= 0.6f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
            eyeZ += 1.0f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
            eyeZ -= 1.0f;
        }

        /*
         * keys to control the ferrisWheel
         * */
        if (controlMap.get(MAP_KEY_FERRIS_WHEEL)){
            //start or stop the ferris wheel
            if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
                isFerrisWheelStart = !isFerrisWheelStart;
            }
            //change the speed of the ferris wheel
            if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                speedFW += 1.0f;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                speedFW -= 1.0f;
            }

        }

        /*
         * keys to control the corsair
         * */
        if (controlMap.get(MAP_KEY_CORSAIR)){
            if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
                isCorsairStart = !isCorsairStart;
            }
        }

        /*
         * keys to control the terrorist
         * */
        if (controlMap.get(MAP_KEY_HUMAN)){
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                //start moving when any of the key a, s, d, w is pressed
                humanIsMoving = true;
                //set the orientation as "a"
                humanOrientation = "a";
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                //start moving when any of the key a, s, d, w is pressed
                humanIsMoving = true;
                //set the orientation as "d"
                humanOrientation = "d";
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                //start moving when any of the key a, s, d, w is pressed
                humanIsMoving = true;
                //set the orientation as "w"
                humanOrientation = "w";
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                //start moving when any of the key a, s, d, w is pressed
                humanIsMoving = true;
                //set the orientation as "s"
                humanOrientation = "s";
            }
            //if any of a, s, d, w is NOT pressed, we stop the human moving
            if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_W)){
            }else {
                //stop the human
                humanIsMoving = false;
            }

            //press (hold) the shift key to dash
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
                speedLimbTerrorist = 20.0f;
                speedTerrorist = 20;
            }else{
                //when releasing the shift, we set the terrorist speed back to the initial value
                speedLimbTerrorist = 10.0f;
                speedTerrorist = 10;
            }

            //holding the space key to let the terrorist jump
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
                humanJumpHeight = 5;
            }else{
                //when the space key released, we close the jumping mode, which means set the jump height to 0
                humanJumpHeight = 0;
            }

            //press key E to interact with the bomb
            if(Keyboard.isKeyDown((Keyboard.KEY_E))){
                //if the terrorist is holding the bomb, we just let him put it down
                if (isHoldingBomb){
                    isHoldingBomb = false;
                    isInitBomb = false; //the bomb is picked up, so the status of it is no longer the "init"

                    //after the bomb is dropped, we should record the current location of the terrorist to generate a proper location for the bomb
                    if (humanOrientation.equals("d")){
                        newBombX = -500 + terroristOffsetX + 190;
                        newBombY = 355 + terroristOffsetY;
                        newBombZ = terroristOffsetZ;
                    }else if(humanOrientation.equals("a")){
                        newBombX = -500 + terroristOffsetX - 190;
                        newBombY = 355 + terroristOffsetY;
                        newBombZ = terroristOffsetZ;
                    }else if(humanOrientation.equals("w")){
                        newBombX = -500 + terroristOffsetX;
                        newBombY = 355 + terroristOffsetY;
                        newBombZ = terroristOffsetZ + 190;
                    }else if(humanOrientation.equals("s")){
                        newBombX = -500 + terroristOffsetX;
                        newBombY = 355 + terroristOffsetY;
                        newBombZ = terroristOffsetZ - 190;
                    }

                    //if the terrorist is NOT holding the bomb, we validate whether the bomb is reachable, then let him pick it up
                }else{
                    if (isBombReachable()){
                        isHoldingBomb = true;
                        isInitBomb = false; //the bomb is picked up, so the status of it is no longer the "init"
                    }
                }
            }

            //press key Q to start the bomb (only when the bomb has been planted(not init) and on the ground)
            if (!isHoldingBomb && !isInitBomb){
                if(Keyboard.isKeyDown((Keyboard.KEY_Q))){
                    isPlanted = true;   // start the bomb
                    //record the current time of the start time of the bomb
                    bombPlantedTime = getTime();

                    //play the audio "Bomb has been planted"
                    if (!isAudioPlay){  //make sure it is only played a single time
                        try {
                            FileInputStream bomb_mp3 = new FileInputStream("res/bomb_planted.wav");
                            AudioStream asBomb = new AudioStream(bomb_mp3);
                            AudioPlayer.player.start((asBomb));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        isAudioPlay = true; //lock this "if" block
                    }

                }
            }




        }



//        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
//            Earth = !Earth;
//        }

        if (waitForKeyrelease) // check done to see if key is released
        {
            if (Keyboard.isKeyDown(Keyboard.KEY_G)) {

                DRAWGRID = !DRAWGRID;
                Keyboard.next();
                if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
                    waitForKeyrelease = true;
                } else {
                    waitForKeyrelease = false;

                }
            }
        }

        /** to check if key is released */
        if (Keyboard.isKeyDown(Keyboard.KEY_G) == false) {
            waitForKeyrelease = true;
        } else {
            waitForKeyrelease = false;

        }


        // keep quad on the screen
        if (x < 0)
            x = 0;
        if (x > 1200)
            x = 1200;
        if (y < 0)
            y = 0;
        if (y > 800)
            y = 800;

        updateFPS(); // update FPS Counter

        LastMouseX = MouseX;
        LastMouseY = MouseY;
    }

    /**
     * Determine whether the bomb is reachable
     * @return true means the terrorist is in the scale that can reach the bomb
     */
    private boolean isBombReachable(){
        //get the current location of the terrorist
        float tX = -500 + terroristOffsetX;
        float tY = 400 + terroristOffsetY;
        float tZ = 0 + terroristOffsetZ;

        //the current location of the bomb
        float bX;
        float bY;
        float bZ;

        //determine the current location of the bomb
        if (isInitBomb){
            //if the bomb has never picked up once, this should be a static position
            bX = 1000;
            bY = 355;
            bZ = 700;
        }else{
            bX = newBombX;
            bY = newBombY;
            bZ = newBombZ;
        }

        //calculate the distance between the terrorist and the bomb
        double distance = Math.sqrt(Math.pow((tX - bX), 2) + Math.pow((tY - bY), 2) + Math.pow((tZ - bZ), 2));

        //for test
//        System.out.println("distance: " + distance);

        if (distance < 250){
            return true;
        }else{
            return false;
        }

    }

    /**
     * Calculate how many milliseconds have passed since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public void initGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        changeOrth();
        MyArcball.startBall(0, 0, 1200, 800);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

//        FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);
//        lightPos.put(-10000f).put(-1000f).put(-1000).put(0).flip();
//
//        FloatBuffer lightPos2 = BufferUtils.createFloatBuffer(4);
//        lightPos2.put(0f).put(-1000f).put(0).put(1000f).flip();
//
//        FloatBuffer lightPos3 = BufferUtils.createFloatBuffer(4);
//        lightPos3.put(10000f).put(-1000f).put(-1000).put(0).flip();
//
//        FloatBuffer lightPos4 = BufferUtils.createFloatBuffer(4);
//        lightPos4.put(-1000f).put(-1000f).put(-1000f).put(0).flip();

        FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);
        lightPos.put(10000f).put(1000f).put(1000).put(0).flip();

        FloatBuffer lightPos2 = BufferUtils.createFloatBuffer(4);
        lightPos2.put(0f).put(1000f).put(0).put(-1000f).flip();

        FloatBuffer lightPos3 = BufferUtils.createFloatBuffer(4);
        lightPos3.put(10000f).put(-1000f).put(-1000).put(0).flip();

        FloatBuffer lightPos4 = BufferUtils.createFloatBuffer(4);
        lightPos4.put(-1000f).put(-1000f).put(-1000f).put(0).flip();

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPos); // specify the
        // position
        // of the
        // light
        // GL11.glEnable(GL11.GL_LIGHT0); // switch light #0 on // I've setup specific materials so in real light it will look abit strange

        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPos2); // specify the
        // position
        // of the
        // light
        GL11.glEnable(GL11.GL_LIGHT1); // switch light #0 on
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, Utils.ConvertForGL(spot));

        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_POSITION, lightPos3); // specify
        // the
        // position
        // of the
        // light
        GL11.glEnable(GL11.GL_LIGHT2); // switch light #0 on
        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));

        GL11.glLight(GL11.GL_LIGHT3, GL11.GL_POSITION, lightPos4); // specify
        // the
        // position
        // of the
        // light
        GL11.glEnable(GL11.GL_LIGHT3); // switch light #0 on
        GL11.glLight(GL11.GL_LIGHT3, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));

        GL11.glEnable(GL11.GL_LIGHTING); // switch lighting on
        GL11.glEnable(GL11.GL_DEPTH_TEST); // make sure depth buffer is switched
        // on
        GL11.glEnable(GL11.GL_NORMALIZE); // normalize normal vectors for safety
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        try {
            init();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //load in texture
    }


    public void changeOrth() {

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(1200 - OrthoNumber, OrthoNumber, (800 - (OrthoNumber * 0.66f)), (OrthoNumber * 0.66f), 100000, -100000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        FloatBuffer CurrentMatrix = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, CurrentMatrix);

        //	if(MouseOnepressed)
        //	{

        MyArcball.getMatrix(CurrentMatrix);
        //	}

        GL11.glLoadMatrix(CurrentMatrix);

    }

    /*
     * You can edit this method to add in your own objects /  remember to load in textures in the INIT method as they take time to load
     *
     */
    public void renderGL() {
        changeOrth();

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        myDelta = getTime() - StartTime;

        // code to aid in animation

        //for running human
        float delta = ((float) myDelta) / 10000;
        float theta = (float) (delta * 2 * Math.PI);    // (thetaRad)
        float thetaDeg = delta * 360;
        float posn_x = (float) Math.cos(theta); // same as your circle code in your notes
        float posn_y = (float) Math.sin(theta);

        //for walking human
        float delta_walk = ((float) myDelta) / 20000;
        float theta_walk = (float) (delta_walk * 2 * Math.PI);    // (thetaRad)
        float thetaDeg_walk = delta_walk * 360;
        float posn_x_walk = (float) Math.cos(theta_walk); // same as your circle code in your notes
        float posn_y_walk = (float) Math.sin(theta_walk);

        /*
         * This code draws a grid to help you view the human models movement
         *  You may change this code to move the grid around and change its starting angle as you please
         */
        if (DRAWGRID) {
            GL11.glPushMatrix();
            Grid MyGrid = new Grid();
//			GL11.glTranslatef(600, 400, 0);
            GL11.glTranslatef(600, 265, 0);
//			GL11.glScalef(200f, 200f,  200f);
            GL11.glScalef(100f, 100f, 100f);
            MyGrid.DrawGrid();
            GL11.glPopMatrix();
        }


        float deltaLook = ((float) myDelta) / 10000;
        float deltaLookTrembling = ((float) myDelta) / 10;
        //set the position of the camera (2 different modes, trembling and normal)
        float thetaLook = (float) (deltaLook * 2 * Math.PI);
        float thetaLookTrembling = (float) (deltaLookTrembling * 2 * Math.PI);
        float camSwing = (float) Math.cos(thetaLook);
        float camSwingTrembling = (float) Math.cos(thetaLookTrembling) * 2.5f;

        //if we are controlling the terrorist, we will zoom in
        if (controlMap.get(MAP_KEY_HUMAN)){

            //normal mode
            GLU.gluLookAt(-517.5f, 10.4f, 1373.0f, -500 + terroristOffsetX, 400 + terroristOffsetY, terroristOffsetZ, 0, 1, 0);

            //after a specific time, we stop the window trembling
            if (isWindowTrembling && getTime() > winTremblingStartTime + winTremblingDelay){
                isWindowTrembling = false;
            }

            //we only zoom in once automatically, then the user can still control the zoom
            if (!isZoomedInOnce){
                OrthoNumber = 2200; //zoom in into the terrorist
                isZoomedInOnce = true;  //lock this "if" statement
            }


        //if we are NOT controlling the terrorist, we will NOT zoom in
        }else{
            GLU.gluLookAt((camSwing*12.5f) + eyeX, eyeY, eyeZ, 0, 0, 0, 0, 1, 0);
            if (isZoomedInOnce){
                OrthoNumber = 10000; //zoom out from the terrorist
                isZoomedInOnce = false; //reset the zoom lock
            }
        }

        //if the window should tremble, we let it tremble (This make sure to tremble also when we are not focusing on the terrorist)
        if (isWindowTrembling && getTime() <= winTremblingStartTime + winTremblingDelay ){
            GLU.gluLookAt((camSwingTrembling) + eyeX, camSwingTrembling*0.6f + eyeY, camSwingTrembling*0.8f + eyeZ, 0, 0, 0, 0, 1, 0);
        }
        if (isWindowTrembling && getTime() > winTremblingStartTime + winTremblingDelay){
            isWindowTrembling = false;
        }


        /*
            Draw the plying child (running human)  -----------------------------------------------------------------------------------------------
         */
        GL11.glPushMatrix();
        Human MyHuman = new Human(headTexture, chestTexture, null, shadowTexture,  "run");
        GL11.glTranslatef(-5000, 400, 0);
        GL11.glScalef(90f, 90f, 90f);

        //correct the postion for the human rotating
        /** revolution (anticlockwise(from top)) **/
        GL11.glTranslatef(posn_x * 6.0f, 0.0f, posn_y * 6.0f);
        /** spin (anticlockwise (from top)) **/
        GL11.glRotatef(-thetaDeg, 0.0f, 1.0f, 0.0f);

        // check the speed for test
//		System.out.println("speed: " + speed);

        try {
            MyHuman.DrawHuman(delta * speed, null, false, 0, false); // give a delta for the Human object ot be animated
        } catch (IOException e) {
            e.printStackTrace();
        }

        GL11.glPopMatrix();
        // --------------------------------------------------------------------------------------------------------------------

        /*
            Draw the playing child (walking human)  -----------------------------------------------------------------------------------------------
         */
        GL11.glPushMatrix();
        Human walkingHuman = new Human(headTexture, chestTexture, null, shadowTexture, "walk");
        GL11.glTranslatef(-6000, 400, 0);
//        GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(90f, 90f, 90f);

        //correct the postion for the human rotating
        /** revolution (anticlockwise(from top)) **/
        GL11.glTranslatef(posn_x_walk * 12.0f, 0.0f, posn_y_walk * 12.0f);
        /** spin (anticlockwise (from top)) **/
        GL11.glRotatef(-thetaDeg_walk, 0.0f, 1.0f, 0.0f);

        // check the speed for test
//		System.out.println("speed: " + speed);

        try {
            walkingHuman.DrawHuman(delta * speed / 2, null, false, 0, false); // give a delta for the Human object ot be animated
        } catch (IOException e) {
            e.printStackTrace();
        }

        GL11.glPopMatrix();
        // --------------------------------------------------------------------------------------------------------------------


        /*
            Draw the terrorist (the human that we are able to control)  -----------------------------------------------------------------------------------------------
        */
        GL11.glPushMatrix();
        Human terrorist = new Human(headTexture, chestTexture, bombTexture, shadowTexture, "beControlled");

        // move the terrorist by changing the location offsets
        if (humanIsMoving) {
            if (humanOrientation.equals("a")) {
                terroristOffsetX -= speedTerrorist;

            } else if (humanOrientation.equals("d")) {
                terroristOffsetX += speedTerrorist;

            } else if (humanOrientation.equals("w")) {
                terroristOffsetZ += speedTerrorist;

            } else if (humanOrientation.equals("s")) {
                terroristOffsetZ -= speedTerrorist;

            }
        }

        GL11.glTranslatef(-500 + terroristOffsetX, 400 + terroristOffsetY, 0 + terroristOffsetZ);
//        GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(90f, 90f, 90f);

//        //correct the postion for the human rotating
//        /** revolution (anticlockwise(from top)) **/
//        GL11.glTranslatef(posn_x_walk * 12.0f, 0.0f, posn_y_walk * 12.0f);
//        /** spin (anticlockwise (from top)) **/
//        GL11.glRotatef(-thetaDeg_walk, 0.0f, 1.0f, 0.0f);

        try {
            terrorist.DrawHuman(delta * speedLimbTerrorist / 2, humanOrientation, humanIsMoving, humanJumpHeight, isHoldingBomb); // give a delta for the Human object ot be animated
        } catch (IOException e) {
            e.printStackTrace();
        }

        GL11.glPopMatrix();
        // --------------------------------------------------------------------------------------------------------------------


        /*
            Draw the ferris wheel  -----------------------------------------------------------------------------------------------
         */
        //determine whether the ferris wheel is destroyed
        if (isExplode){
            GL11.glPushMatrix();
            FerrisWheel ferrisWheel = new FerrisWheel(fwRoomTexture);
            GL11.glTranslatef(-2500, 1800, 0);
            GL11.glRotatef(15, 0.0f, -1.0f, 0.0f);
            GL11.glScalef(90f, 90f, 90f);

            //fall down
            GL11.glRotatef(100, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0, 0, 10);

            ferrisWheel.draw(delta, isFerrisWheelStart, speedFW); // give a delta for the FerrisWheel object ot be animated
            GL11.glPopMatrix();
        }else{
            GL11.glPushMatrix();
            FerrisWheel ferrisWheel = new FerrisWheel(fwRoomTexture);
            GL11.glTranslatef(-2500, 1800, 0);
            GL11.glRotatef(15, 0.0f, -1.0f, 0.0f);
            GL11.glScalef(90f, 90f, 90f);

            ferrisWheel.draw(delta, isFerrisWheelStart, speedFW); // give a delta for the FerrisWheel object ot be animated
            GL11.glPopMatrix();
        }
        // --------------------------------------------------------------------------------------------------------------------


        /*
            Draw the Corsair  -----------------------------------------------------------------------------------------------
         */
        //determine whether the ferris wheel is destroyed
        if (isExplode){
            GL11.glClearColor(1, 1, 1, 1);
            GL11.glColor3f(blue[0], blue[1], blue[2]);
            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));

            GL11.glPushMatrix();
            Corsair corsair = new Corsair(corsairTexture);
            GL11.glTranslatef(4300, 3000, 0);
            GL11.glRotatef(15, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(90f, 90f, 90f);

            //fall down
            GL11.glRotatef(102, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0, 0, 16);

            corsair.draw(delta, isCorsairStart); // give a delta for the FerrisWheel object ot be animated

            GL11.glPopMatrix();
        }else{
            GL11.glClearColor(1, 1, 1, 1);
            GL11.glColor3f(blue[0], blue[1], blue[2]);
            GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));

            GL11.glPushMatrix();
            Corsair corsair = new Corsair(corsairTexture);
            GL11.glTranslatef(4300, 3000, 0);
            GL11.glRotatef(15, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(90f, 90f, 90f);

            corsair.draw(delta, isCorsairStart); // give a delta for the FerrisWheel object ot be animated

            GL11.glPopMatrix();
        }

        // --------------------------------------------------------------------------------------------------------------------

        /*
            Draw the Ticket Office  -----------------------------------------------------------------------------------------------
         */
        GL11.glPushMatrix();
        TicketOffice ticketOffice = new TicketOffice(ticketOfficeSignTexture, buildingBodyTexture, shadowTexture);
        GL11.glTranslatef(-7300, 900, -18000);
        GL11.glScalef(70f, 70f, 70f);
        GL11.glRotatef(45, 0.0f, 1.0f, 0.0f);

        ticketOffice.draw(delta); // give a delta for the FerrisWheel object ot be animated

        GL11.glPopMatrix();

        /*
            draw shadow for ticket office
         */
        GL11.glPushMatrix();
        TexCube houseShadow = new TexCube();
        GL11.glTranslatef(-3700, 320, -18000);
        GL11.glRotatef(90, 0.1f, 0.0f, 0.0f);

        // make the cube into a sign (2D surface)
        GL11.glScalef(2400f, 1140f, 0f);

        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_CLAMP);

        Color.white.bind();
        shadowTexture.bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
        GL11.glEnable(GL11.GL_BLEND);

        houseShadow.DrawTexCube();
        GL11.glPopMatrix();
        // --------------------------------------------------------------------------------------------------------------------

        /*
            Draw some trees  -----------------------------------------------------------------------------------------------
         */
        //tree1
        GL11.glPushMatrix();
        Tree tree1 = new Tree(treeTopTexture, shadowTexture);
        GL11.glTranslatef(0, -1400, -10000);
        GL11.glScalef(70f, 70f, 70f);
        tree1.draw();
        GL11.glPopMatrix();

        //tree2
        GL11.glPushMatrix();
        Tree tree2 = new Tree(treeTopTexture, shadowTexture);
        GL11.glTranslatef(-10000, -1400, -15000);
        GL11.glScalef(70f, 70f, 70f);
        tree2.draw();
        GL11.glPopMatrix();

        //tree3
        GL11.glPushMatrix();
        Tree tree3 = new Tree(treeTopTexture, shadowTexture);
        GL11.glTranslatef(-5600, -1400, -15000);
        GL11.glScalef(70f, 70f, 70f);
        tree3.draw();
        GL11.glPopMatrix();

        //tree4
        GL11.glPushMatrix();
        Tree tree4 = new Tree(treeTopTexture, shadowTexture);
        GL11.glTranslatef(-4000, -1400, -12500);
        GL11.glScalef(70f, 70f, 70f);
        tree4.draw();
        GL11.glPopMatrix();

        //tree5
        GL11.glPushMatrix();
        Tree tree5 = new Tree(treeTopTexture, shadowTexture);
        GL11.glTranslatef(-2000, -1400, -12500);
        GL11.glScalef(70f, 70f, 70f);
        tree5.draw();
        GL11.glPopMatrix();
        // --------------------------------------------------------------------------------------------------------------------


        /*
         * Draw the background 2021.png -------------------------------------------------------------------------------------------
         */
        //Textured Sign beside the textured Cube in the scene
        GL11.glPushMatrix();
        TexCube background2021 = new TexCube();
        GL11.glTranslatef(6000, 1800, 2700);
        GL11.glTranslatef(0, 0, 8000);

        // make the cube into a sign (2D surface)
        GL11.glScalef(15000f, 7400f, 0.0f);

        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_CLAMP);

        Color.white.bind();
        background2021Texture.bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);


        background2021.DrawTexCube();
        GL11.glPopMatrix();
        //-------------------------------------------------------------------------------------------------------------------------

        /*
         * Draw the ground  -------------------------------------------------------------------------------------------
         */
        //Textured Sign beside the textured Cube in the scene
        GL11.glPushMatrix();
        TexCube ground = new TexCube();
        GL11.glTranslatef(500, 265, 300);
        GL11.glTranslatef(0, 0, -10000);


        // make the cube into a sign (2D surface)
        GL11.glScalef(24000f, 0.0f, 24000f);

        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                GL11.GL_CLAMP);

        Color.white.bind();
        groundTexture.bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);


        ground.DrawTexCube();
        GL11.glPopMatrix();
        //-------------------------------------------------------------------------------------------------------------------------


        /*
         *	Draw the textured Sign that is required on csmoodle -------------------------------------------------------------------
         */
//        //Textured Sign beside the textured Cube in the scene
//        GL11.glPushMatrix();
//        TexCube MySign = new TexCube();
//        GL11.glTranslatef(500, 500, 700);
//
//        // make the cube into a sign (2D surface)
//        GL11.glScalef(140f, 140f, 0f);
//
//        GL11.glTexParameteri(
//                GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
//                GL11.GL_CLAMP);
//
//        Color.white.bind();
//        cubeTexture.bind();
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
//        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
//
//
//        MySign.DrawTexCube();
//        GL11.glPopMatrix();
        // --------------------------------------------------------------------------------------------------------------------


        /*
         * Draw the Bomb on the ground (only when the terrorist is NOT holding the bomb and the bomb has not been exploded, we draw the bomb on the ground) -------------------------------------------------------------------------------------------------------------
         */
        //determine whether the bomb is exploded (calculate whether the time passed after the bomb being planted reaches the bomb delay)
        if (isPlanted){
            //we should only get in to the following "if" a single time to start exploding (window trembling and so on)
            if (!isExploding && getTime() - bombPlantedTime >= bombDelay){
                isExplode = true;
                //start the window trembling
                winTremblingStartTime = getTime();
                isWindowTrembling = true;

                isExploding = true; //lock this "if" statements block

                //stop the ferris wheel and corsair
                isFerrisWheelStart = false;
                isCorsairStart = false;
            }
        }
        //draw
        if (!isHoldingBomb && !isExplode){
            //Textured Cube beside the textured Sign in the scene
            GL11.glPushMatrix();
            TexCube bombOnGround = new TexCube();

            //at the beginning, we will put the bomb at a specific place otherwise, we will put it besides the terrorist
            if (isInitBomb){
                GL11.glTranslatef(1000, 355, 700);
            }else{
                //every time the terrorist put it down, it should besides the terrorist
                GL11.glTranslatef(newBombX, newBombY, newBombZ);

            }

            GL11.glScalef(90f, 90f, 90f);    //make the cube a cube

            GL11.glTexParameteri(
                    GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                    GL11.GL_CLAMP);

            Color.white.bind();

            //give different texture according to the different status of the bomb
            if(isPlanted){
                bombPlantedTexture.bind();
            }else{
                bombTexture.bind();
            }

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

            bombOnGround.DrawTexCube();
            GL11.glPopMatrix();
        }
        //------------------------------------------------------------------------------------------------------------------------------

        /*
         * Draw the Hint sign to Pick up the bomb (only when the terrorist is able to reach the bomb and not holding the bomb, there will be a hint) -------------------------------------------------------------------------------------------------------------
         */
        if (!isHoldingBomb && isBombReachable()){
            //Textured Cube beside the textured Sign in the scene
            GL11.glPushMatrix();
            TexCube bombHint = new TexCube();

            //at the beginning, we will put the bomb at a specific place otherwise, we will put it besides the terrorist
            if (isInitBomb){
                GL11.glTranslatef(1000, 555, 700);
            }else{
                //get the current location of the bomb
                float newBombHintX = -500 + terroristOffsetX + 190;
                float newBombHintY = 355 + terroristOffsetY + 200;
                float newBombHintZ = terroristOffsetZ;
                //every time the terrorist put it down, it should besides the terrorist
                GL11.glTranslatef(newBombHintX, newBombHintY, newBombHintZ);
            }

            GL11.glTranslatef(0.0f, 500.0f, 0.0f);
            GL11.glRotatef(90, 0.0f, 0.0f, 1.0f);
            GL11.glScalef(400f, 800f, 0.0f);    //make the cube a 2D sign

            GL11.glTexParameteri(
                    GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                    GL11.GL_CLAMP);

            Color.white.bind();
            bombHintTexture.bind();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

            bombHint.DrawTexCube();
            GL11.glPopMatrix();


        }
        //------------------------------------------------------------------------------------------------------------------------------


        /*
         * Draw the Countdown clock sign (only when the bomb is planted and before exploding, this will be drawn, every second the texture will be changed!) -------------------------------------------------------------------------------------------------------------
         */
        if (isPlanted && !isExplode){
            GL11.glPushMatrix();
            TexCube countDownClock = new TexCube();

            //record the current bomb location for countdown clock position
            if (!isCountdownPositionRecorded){
                countdownX = -500 + terroristOffsetX + 190;
                countdownY = 355 + terroristOffsetY + 200;
                countdownZ = terroristOffsetZ;
                isCountdownPositionRecorded = true; //lock this "if statement"
            }

            //set the countdown clock at a static location
            GL11.glTranslatef(countdownX, countdownY, countdownZ);

            GL11.glTranslatef(0.0f, 1500.0f, 0.0f);
            GL11.glRotatef(90, 0.0f, 0.0f, 1.0f);
            GL11.glScalef(400f, 800f, 0.0f);    //make the cube a 2D sign

            GL11.glTexParameteri(
                    GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                    GL11.GL_CLAMP);

            Color.white.bind();

            //determine which texture to use according to the countdown time
            if (getTime() - bombPlantedTime < 1000){
                countDownTexture5.bind();

            }else if (getTime() - bombPlantedTime >= 1000 && getTime() - bombPlantedTime < 2000){
                countDownTexture4.bind();


            }else if (getTime() - bombPlantedTime >= 2000 && getTime() - bombPlantedTime < 3000){
                countDownTexture3.bind();


            }else if (getTime() - bombPlantedTime >= 3000 && getTime() - bombPlantedTime < 4000){
                countDownTexture2.bind();


            }else if (getTime() - bombPlantedTime >= 4000 && getTime() - bombPlantedTime < 5000){
                countDownTexture1.bind();


            }else if (getTime() - bombPlantedTime >= 5000 && getTime() - bombPlantedTime < 6000){
                countDownTexture0.bind();


            }

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

            countDownClock.DrawTexCube();
            GL11.glPopMatrix();
        }
        //------------------------------------------------------------------------------------------------------------------------------




        /*
         * This code puts the earth code in which is larger than the human so it appears to change the scene
         */
        if (Earth) {
            //Globe in the centre of the scene
            GL11.glPushMatrix();
//			 TexSphere MyGlobe = new TexSphere();
            TexCube MyGlobe = new TexCube();
            GL11.glTranslatef(500, 500, 500);

            GL11.glScalef(140f, 140f, 140f);    // a cube
//			GL11.glScalef(140f, 140f, 0f);    // a sign (2D surface)

            GL11.glTexParameteri(
                    GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                    GL11.GL_CLAMP);

            Color.white.bind();
//			    sphereTexture.bind();
            cubeTexture.bind();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

//		 	MyGlobe.DrawTexSphere(8f, 100, 100, sphereTexture);
            MyGlobe.DrawTexCube();
            GL11.glPopMatrix();
        }

    }

    public static void main(String[] argv) {
        MainWindow hello = new MainWindow();
        hello.start();
    }

    /* human */
    Texture sphereTexture;    // the texture of the sphere
    Texture cubeTexture;    // the texture of the cube
    Texture headTexture;    // the texture of the human head
    Texture chestTexture;    // the texture of the human body
    /* background */
    Texture background2021Texture; // the texture of the background
    Texture groundTexture; // the texture of the ground
    /* ticket office */
    Texture ticketOfficeSignTexture;
    Texture buildingBodyTexture;
    /* Ferris Wheel */
    Texture fwRoomTexture;
    /* Corsair */
    Texture corsairTexture;
    /* Tree */
    Texture treeTopTexture;
    /* the texture for shadow */
    Texture shadowTexture;
    /* the texture for the bomb */
    Texture bombTexture;
    /* the texture for the bomb when it is planted (started(going to explode)) */
    Texture bombPlantedTexture;
    /* the texture for the bomb hint */
    Texture bombHintTexture;
    /* the texture for the bomb count down clock 5sec */
    Texture countDownTexture5;
    /* the texture for the bomb count down clock 4sec */
    Texture countDownTexture4;
    /* the texture for the bomb count down clock 3sec */
    Texture countDownTexture3;
    /* the texture for the bomb count down clock 2sec */
    Texture countDownTexture2;
    /* the texture for the bomb count down clock 1sec */
    Texture countDownTexture1;
    /* the texture for the bomb count down clock 0sec */
    Texture countDownTexture0;
    


    /*
     * Any additional textures for your assignment should be written in here.
     * Make a new texture variable for each one so they can be loaded in at the beginning
     */
    public void init() throws IOException {

        // load the picture resource for the sphere
        sphereTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/earthspace.png"));
        // load the picture resource for the cube
        cubeTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/cubeTexture.png"));
        // load the picture resource for the human head
        headTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/head4_1.png"));
        // load the picture resource for the human body
        chestTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/chestTexture.png"));
        // load the picture resource for the background 2021
        background2021Texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/2021.png"));
        // load the picture resource for the ground
        groundTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ground.png"));
        // load the picture resource for the ticket office sign
        ticketOfficeSignTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/TicketSign.png"));
        // load the picture resource for the ticket office body
        buildingBodyTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/window.png"));
        // load the picture resource for the room on the ferris wheel
        fwRoomTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/glass.png"));
        // load the picture resource for the corsair
        corsairTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/corsair.png"));
        // load the picture resource for the tree top
        treeTopTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/grass.png"));
        // load the picture resource for the shadow
        shadowTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/shadow.png"));
        // load the picture resource for the bomb
        bombTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/TNT.png"));
        // load the picture resource for the bomb hint
        bombHintTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/BombHint.png"));
        // load the picture resource for the bomb that is gonging to explode
        bombPlantedTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/TNT_explode.png"));
        // load the picture resource for the bomb count down clock (5sec)
        countDownTexture5 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/countdown5.png"));
        // load the picture resource for the bomb count down clock (4sec)
        countDownTexture4 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/countdown4.png"));
        // load the picture resource for the bomb count down clock (3sec)
        countDownTexture3 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/countdown3.png"));
        // load the picture resource for the bomb count down clock (2sec)
        countDownTexture2 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/countdown2.png"));
        // load the picture resource for the bomb count down clock (1sec)
        countDownTexture1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/countdown1.png"));
        // load the picture resource for the bomb count down clock (0sec)
        countDownTexture0 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/countdown0.png"));

        System.out.println("Texture loaded okay ");
    }
}
