package advancedObjects;

import objects3D.Cylinder;
import objects3D.Sphere;
import objects3D.TexCube;
import objects3D.TexSphere;
import org.lwjgl.opengl.GL11;
import GraphicsObjects.Point4f;
import GraphicsObjects.Utils;
import GraphicsObjects.Vector4f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class Human {

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


	/** define the texture of each part in this human body **/
	private Texture textureHead;	// head
	private Texture textureChest;	// chest
	private Texture textureShadow;	// shadow
	private Texture textureBomb;	// bomb

	//the task of this human
	private String task;

	//the orientation of the human, only exists when the task is "beControlled", in other cases, this should be null.\
	private String orientation;

	
	public Human(Texture textureHead, Texture textureChest, Texture textureBomb, Texture textureShadow, String task) {
		//initialize the texture of each part in this human body
		this.textureHead = textureHead;
		this.textureChest = textureChest;
		this.textureBomb = textureBomb;
		this.textureShadow = textureShadow;
		this.task = task;	// define the animation of this human
	}


	/**
	 * Define how to draw a specific frame of this human
	 * @param delta the time delta
	 * @param orientation if the task is "beControlled", this should be "w", "s", "a", "d", in other cases, this should be null
	 * @param isMoving if the task is "beControlled", this should be defined. when any a, s, d, w key is pressed, this should be true, otherwise, this should be false. In other tasks, this is useless, whatever true or false
	 * @param jumpHeight this controls the human jump or not, if not in jumping mode, it should be 0. if not 0, it should be the height of the jumping
	 * @param isHoldingBomb the status of whether the terrorist is holding the bomb
	 */
	public void DrawHuman(float delta, String orientation, boolean isMoving, int jumpHeight, boolean isHoldingBomb) throws IOException {
		 float theta = (float) (delta * 2 * Math.PI);
		  float LimbRotation;

		 /** Define the rotation degree of each limbo section **/
		 float leftArmRotation;
		 float rightArmRotation;
		 float leftForeArmRotation;
		 float rightForeArmRotation;
		 float leftHighLegRotation;
		 float rightHighLegRotation;
		 float leftLowLegRotation;
		 float rightLowLegRotation;

		 // the rotation degree of the chest
	 	 float chestRotation;

		 // the rotation degree of the pelvis
	 	 float pelvisRotation;

		 // the fluctuation of body when walking or running (defined on pelvis)
		 float bodyFluctuate;

		 // the jump coefficient of the human (up and down), similar with the bodyFluctuate
		 float jumpCoefficient = 0;

		// after pressing the key a
		if (task.equals("run") || isMoving) {
			LimbRotation = (float) Math.cos(theta) * 45;

//			 System.out.println("LimbRotation: " + LimbRotation + " --- cos: " + Math.cos(theta));	// for test

			/** initialize the rotation degree of each limbo section **/

			//if the terrorist is holding the bomb, his arms should be static
			if (!isHoldingBomb){
				leftArmRotation = LimbRotation;										//left arm
				rightArmRotation = -leftArmRotation;								//right arm

				leftForeArmRotation = (float) Math.cos(theta) * 45 - 30;			//left forearm
				rightForeArmRotation = (float) -Math.cos(theta) * 45 - 30;			//right forearm
			}else{
				leftArmRotation = 0;		//left arm
				rightArmRotation = 0;		//right arm
				leftForeArmRotation = 0;			//left forearm
				rightForeArmRotation = 0;			//right forearm
			}

			leftHighLegRotation = (-LimbRotation / 0.8f);						//left high leg
			rightHighLegRotation = (LimbRotation / 0.8f);						//right high leg

			leftLowLegRotation = (float) Math.cos(theta) * 20 - 20;				//left low leg
			rightLowLegRotation = (float) -Math.cos(theta) * 20 - 20;			//right low lwg

			// initialize the rotation degree of the chest
			chestRotation = (float) Math.cos(theta) * 30;						//chest
			// initialize the rotation of the pelvis
			pelvisRotation = (float) (Math.cos(theta) * -15);					//pelvis

			/*
			 *	 initialize to fluctuate degree of the body
			 *	 cos theta = 1, 0, 1 correspond to body --> up, down, up
			 *	 therefore, we should double the frequency of cos theta as the frequency of the body fluctuation
			 *	 so, it should be cos(theta*2).
			 *	 Further, 0.2f is amplitude of the body fluctuation
			 */
			bodyFluctuate = (float) Math.cos(theta * 2) * 0.27f;				//body up and down

			// initialize the jump coefficient of the pelvis(whole human) (similar with body fluctuate)
			jumpCoefficient = (float) (Math.abs(Math.cos(theta)) * jumpHeight);					//jump

		} else if (task.equals("walk")){
			LimbRotation = (float) Math.cos(theta) * 45;

//			 System.out.println("LimbRotation: " + LimbRotation + " --- cos: " + Math.cos(theta));	// for test

			/** initialize the rotation degree of each limbo section **/
			leftArmRotation = LimbRotation;										//left arm
			rightArmRotation = -leftArmRotation;								//right arm

			leftForeArmRotation = (float) Math.cos(theta) * 45 - 45;			//left forearm
			rightForeArmRotation = (float) -Math.cos(theta) * 45 - 45;			//right forearm

			leftHighLegRotation = (-LimbRotation / 1.8f);						//left high leg
			rightHighLegRotation = (LimbRotation / 1.8f);						//right high leg

			leftLowLegRotation = (float) Math.cos(theta) * 20 - 20;				//left low leg
			rightLowLegRotation = (float) -Math.cos(theta) * 20 - 20;			//right low lwg

			// initialize the rotation degree of the chest
			chestRotation = (float) Math.cos(theta) * 15;						//chest
			// initialize the rotation of the pelvis
			pelvisRotation = (float) (Math.cos(theta) * -10);					//pelvis

			/*
			 *	 initialize to fluctuate degree of the body
			 *	 cos theta = 1, 0, 1 correspond to body --> up, down, up
			 *	 therefore, we should double the frequency of cos theta as the frequency of the body fluctuation
			 *	 so, it should be cos(theta*2).
			 *	 Further, 0.2f is amplitude of the body fluctuation
			 */
			bodyFluctuate = (float) Math.cos(theta * 2) * 0.27f;				//body up and down

		} else {	// before pressing the key a
			LimbRotation = 0;

			// initialize the rotation degree of each limbo section
			leftArmRotation = 0;
			rightArmRotation = 0;
			leftForeArmRotation = 0;
			rightForeArmRotation = 0;
			leftHighLegRotation = 0;
			rightHighLegRotation = 0;
			leftLowLegRotation = 0;
			rightLowLegRotation = 0;

			// initialize the rotation degree of the chest
			chestRotation = 0;
			// initialize the rotation degree of the pelvis
			pelvisRotation = 0;

			// initialize to fluctuate degree of the body
			bodyFluctuate = 0;
		}


		/**
		 * if the task of the human is beControlled, we need to do something more
		 */
		if (task.equals("beControlled") && orientation != null){
			//we need to listen to the orientation of him
			if (orientation.equals("w")){	//forward
				//turn: we do not need to turn, because "w" is the default orientation

			}else if (orientation.equals("s")){		//back
				//turn
				GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);

			}else if (orientation.equals("a")){		//left
				//turn
				GL11.glRotatef(-90, 0.0f, 1.0f, 0.0f);

			}else if (orientation.equals("d")){		//right
				//turn
				GL11.glRotatef(90, 0.0f, 1.0f, 0.0f);
			}

		}

		 Sphere sphere= new Sphere();
		 TexSphere texSphere = new TexSphere();	// we can use this to draw sphere parts in the body with texture
		 Cylinder cylinder= new Cylinder();
		TexCube texCube = new TexCube();

		/** the human has different posture if they are in different task */
		if (task.equals("run") || task.equals("beControlled")){
			 GL11.glPushMatrix();

			 {
				 GL11.glTranslatef(0.0f,0.5f,0.0f);

				 // *** for test *** (make it start with a left side view) *** This should be commented when running the program
//			 GL11.glRotatef(-90, 0.0f, 1.0f, 0.0f);
				 // *** for test *** (make it start with a right side view) *** This should be commented when running the program
//			 GL11.glRotatef(90, 0.0f, 1.0f, 0.0f);

				 GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);

				 // rotate the pelvis when running or walking
				 GL11.glRotatef(pelvisRotation, 0.0f, 0.1f, 0.0f);

				 // the whole body should fluctuate when walking or running (up and down)
				 GL11.glTranslatef(0.0f, bodyFluctuate, 0.0f);

				 //jump
				 if (task.equals("beControlled")){	//only the human we are controlling can jump
					 GL11.glTranslatef(0.0f, jumpCoefficient, 0.0f);
				 }

				 //draw the pelvis
				 sphere.DrawSphere(0.5f, 32, 32);

				 /** draw shadow using the squeezed textured cube */
//				 GL11.glPushMatrix();{
//					GL11.glTranslatef(10, -1.7f, 0);
//				 	GL11.glScalef(8f, 0f, 2f);
//					 GL11.glTexParameteri(
//							 GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
//							 GL11.GL_CLAMP);
//					 Color.white.bind();
//					 textureShadow.bind();
//					 GL11.glEnable(GL11.GL_TEXTURE_2D);
//					 GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
//					 GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
//					 GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
//					 GL11.glEnable(GL11.GL_BLEND);
//					 texCube.DrawTexCube();
//
//					GL11.glPopMatrix();
//		 		 }

				 //  chest
				 GL11.glColor3f(green[0], green[1], green[2]);
				 GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(green));
				 GL11.glPushMatrix(); {
				 GL11.glTranslatef(0.0f,0.5f,0.0f);

				 // rotate the chest down to slightly tip the human forward
				 GL11.glRotatef(-15, 1.0f, 0.0f, 0.0f);
				 GL11.glTranslatef(0.0f, 0.3f, -0.1f);

				 // rotating the chest when running or walking
				 GL11.glRotatef(chestRotation, 0.0f, 1.0f, 0.0f);

				 /** use the texSphere to draw the human chest **/
				 /*
				  * This will show a Google sign at the side of the body
				  */
				 GL11.glScalef(1f, 1f,  1f);
				 GL11.glTexParameteri(
						 GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
						 GL11.GL_CLAMP);
				 Color.white.bind();
				 textureChest.bind();
				 GL11.glEnable(GL11.GL_TEXTURE_2D);
				 GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
				 GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
				 texSphere.DrawTexSphere(0.5f, 32, 32, textureChest);


				 // neck
				 GL11.glColor3f(orange[0], orange[1], orange[2]);
				 GL11.glMaterial( GL11.GL_FRONT,  GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
				 GL11.glPushMatrix(); {
					 GL11.glTranslatef(0.0f,0.0f, 0.0f);
					 GL11.glRotatef(-90.0f,1.0f,0.0f,0.0f);
					 //                    GL11.glRotatef(45.0f,0.0f,1.0f,0.0f);
					 cylinder.DrawCylinder(0.15f,0.7f,32);


					 // head
					 GL11.glColor3f(red[0], red[1], red[2]);
					 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(red));
					 GL11.glPushMatrix(); {
						 GL11.glTranslatef(0.0f,0.0f,1.0f);

						 /** use the texSphere to draw the human head **/
						 /*
						  * this will show a smiling on the face
						  */
						 GL11.glScalef(1f, 1f,  1f);
						 GL11.glRotatef(180, 1.0f, 0.0f, 0.0f);
						 GL11.glRotatef(-90, 0.0f, 0.0f, 1.0f);
						 GL11.glTexParameteri(
								 GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
								 GL11.GL_CLAMP);
						 Color.white.bind();
						 textureHead.bind();
						 GL11.glEnable(GL11.GL_TEXTURE_2D);
						 GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
						 GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
						 texSphere.DrawTexSphere(0.5f, 32, 32, textureHead);

						 GL11.glPopMatrix();
					 } GL11.glPopMatrix();


					 // left shoulder
					 GL11.glColor3f(blue[0],blue[1], blue[2]);
					 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
					 GL11.glPushMatrix(); {
						 GL11.glTranslatef(0.5f,0.4f,0.0f);
						 sphere.DrawSphere(0.25f, 32, 32);


						 // left arm
						 GL11.glColor3f(orange[0], orange[1], orange[2]);
						 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
						 GL11.glPushMatrix(); {
							 GL11.glTranslatef(0.0f,0.0f,0.0f);
							 GL11.glRotatef(90.0f,1.0f,0.3f,0.0f);	// when running, elbow should be a little bit outward

							 // rotate the left arm when walking
							 GL11.glRotatef(leftArmRotation,1.0f,0.0f,0.0f);
							 cylinder.DrawCylinder(0.15f,0.7f,32);


							 // left elbow
							 GL11.glColor3f(blue[0], blue[1], blue[2]);
							 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
							 GL11.glPushMatrix(); {
								 GL11.glTranslatef(0.0f,0.0f,0.75f);
								 sphere.DrawSphere(0.2f, 32, 32);

								 //left forearm
								 GL11.glColor3f(orange[0], orange[1], orange[2]);
								 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
								 GL11.glPushMatrix(); {
									 GL11.glTranslatef(0.0f,0.0f,0.0f);
									 //lift the left forearm up
									 GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);

									 // rotate the left forearm when walking or running
									 GL11.glRotatef(leftForeArmRotation,1.0f,0.0f,0.0f);
									 cylinder.DrawCylinder(0.1f,0.7f,32);

									 // left hand
									 GL11.glColor3f(blue[0], blue[1], blue[2]);
									 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
									 GL11.glPushMatrix(); {
										 GL11.glTranslatef(0.0f,0.0f,0.75f);
										 sphere.DrawSphere(0.2f, 32, 32);

										 //draw the bomb if holding the bomb
										 if (isHoldingBomb){
											 GL11.glPushMatrix();{
												 GL11.glTranslatef(0.0f,0.0f,1.3f);
												 GL11.glScalef(1, 1, 1);

												 GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);

												 Color.white.bind();
												 textureBomb.bind();
												 GL11.glEnable(GL11.GL_TEXTURE_2D);
												 GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
												 GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
												 texCube.DrawTexCube();

												 GL11.glPopMatrix();
											 }
										 }


									 } GL11.glPopMatrix();
								 } GL11.glPopMatrix();
							 } GL11.glPopMatrix();
						 } GL11.glPopMatrix ();
					 } GL11.glPopMatrix ();
					 // to chest

					 //************************************************************************************************************************************************************
					 //******************************* Right parts of the arm (start)
					 // right shoulder
					 GL11.glColor3f(blue[0],blue[1], blue[2]);
					 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
					 GL11.glPushMatrix(); {
						 //set the position of the right shoulder
						 GL11.glTranslatef(-0.5f,0.4f,0.0f);
						 //draw right shoulder
						 sphere.DrawSphere(0.25f, 32, 32);


						 // right arm
						 GL11.glColor3f(orange[0], orange[1], orange[2]);
						 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
						 GL11.glPushMatrix(); {

							 //set the position of the right arm
							 GL11.glTranslatef(0.0f,0.0f,0.0f);

							 // when running, elbow should be a little bit outward
							 GL11.glRotatef(90.0f,1.0f,-0.3f,0.0f);

							 // right arm should keep moving when walking or running
							 GL11.glRotatef(rightArmRotation,1.0f,0.0f,0.0f);

							 //draw right arm
							 cylinder.DrawCylinder(0.15f,0.7f,32);


							 // right elbow
							 GL11.glColor3f(blue[0], blue[1], blue[2]);
							 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
							 GL11.glPushMatrix(); {

								 //set the position of the right elbow
								 GL11.glTranslatef(0.0f,0.0f,0.75f);

								 //draw right elbow
								 sphere.DrawSphere(0.2f, 32, 32);

								 //right forearm
								 GL11.glColor3f(orange[0], orange[1], orange[2]);
								 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
								 GL11.glPushMatrix(); {

									 //set the position of the left forearm
									 GL11.glTranslatef(0.0f,0.0f,0.0f);

									 // lift the right forearm up
									 GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);

									 // rotate the right forearm when walking or running
									 GL11.glRotatef(rightForeArmRotation,1.0f,0.0f,0.0f);

									 //draw the right forearm
									 cylinder.DrawCylinder(0.1f,0.7f,32);

									 // right hand
									 GL11.glColor3f(blue[0], blue[1], blue[2]);
									 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
									 GL11.glPushMatrix(); {

										 //set the position of the right hand
										 GL11.glTranslatef(0.0f,0.0f,0.75f);

										 //draw the right hand
										 sphere.DrawSphere(0.2f, 32, 32);



									 } GL11.glPopMatrix();
								 } GL11.glPopMatrix();
							 } GL11.glPopMatrix();
						 } GL11.glPopMatrix ();
					 } GL11.glPopMatrix ();

					 //******************************* Right parts of the arm (end)
					 //************************************************************************************************************************************************************


					 //chest


				 } GL11.glPopMatrix();


				 // pelvis


				 // right hip
				 GL11.glColor3f(blue[0], blue[1], blue[2]);
				 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
				 GL11.glPushMatrix(); {
					 GL11.glTranslatef(-0.5f,-0.2f,0.0f);

					 sphere.DrawSphere(0.25f, 32, 32);


					 // right high leg
					 GL11.glColor3f(orange[0], orange[1], orange[2]);
					 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
					 GL11.glPushMatrix(); {
						 GL11.glTranslatef(0.0f,0.0f,0.0f);
						 GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);


						 // rotate the right high leg when walking or running
						 GL11.glRotatef(rightHighLegRotation,1.0f,0.0f,0.0f);
						 cylinder.DrawCylinder(0.15f,0.7f,32);


						 // right knee
						 GL11.glColor3f(blue[0], blue[1], blue[2]);
						 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
						 GL11.glPushMatrix(); {
							 GL11.glTranslatef(0.0f,0.0f,0.75f);
							 GL11.glRotatef(0.0f,0.0f,0.0f,0.0f);
							 sphere.DrawSphere(0.25f, 32, 32);

							 //right low leg
							 GL11.glColor3f(orange[0], orange[1], orange[2]);
							 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
							 GL11.glPushMatrix(); {
								 GL11.glTranslatef(0.0f,0.0f,0.0f);

								 // rotate the right low leg when walking or running
								 GL11.glRotatef(rightLowLegRotation, 1.0f, 0.0f, 0.0f);
								 cylinder.DrawCylinder(0.15f,0.7f,32);

								 // right foot
								 GL11.glColor3f(blue[0], blue[1], blue[2]);
								 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
								 GL11.glPushMatrix(); {
									 GL11.glTranslatef(0.0f,0.0f,0.75f);
									 sphere.DrawSphere(0.3f, 32, 32);

								 } GL11.glPopMatrix();
							 } GL11.glPopMatrix();
						 } GL11.glPopMatrix();
					 } GL11.glPopMatrix();
				 } GL11.glPopMatrix();

				 // pelvis


				 //************************************************************************************************************************************************************
				 //*******************************  Left parts of the leg (start)
				 // left hip
				 GL11.glColor3f(blue[0], blue[1], blue[2]);
				 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
				 GL11.glPushMatrix(); {

					 //set the position of the left hip
					 GL11.glTranslatef(0.5f,-0.2f,0.0f);

					 //draw the left hip
					 sphere.DrawSphere(0.25f, 32, 32);


					 // left high leg
					 GL11.glColor3f(orange[0], orange[1], orange[2]);
					 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
					 GL11.glPushMatrix(); {

						 //set the position of the left high leg
						 GL11.glTranslatef(0.0f,0.0f,0.0f);

						 //draw the left high leg
						 GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);

						 // rotate the left high leg when walking or running
						 GL11.glRotatef(leftHighLegRotation,1.0f,0.0f,0.0f);

						 //draw the right high leg
						 cylinder.DrawCylinder(0.15f,0.7f,32);


						 // left knee
						 GL11.glColor3f(blue[0], blue[1], blue[2]);
						 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
						 GL11.glPushMatrix(); {

							 //set the position of the left knee
							 GL11.glTranslatef(0.0f,0.0f,0.75f);
							 GL11.glRotatef(0.0f,0.0f,0.0f,0.0f);

							 //draw the left knee
							 sphere.DrawSphere(0.25f, 32, 32);

							 //left low leg
							 GL11.glColor3f(orange[0], orange[1], orange[2]);
							 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
							 GL11.glPushMatrix(); {

								 //set the position of the left low leg
								 GL11.glTranslatef(0.0f,0.0f,0.0f);

								 // rotate the left low leg when walking or running
								 GL11.glRotatef(leftLowLegRotation,1.0f,0.0f,0.0f);

								 //draw the left low leg
								 cylinder.DrawCylinder(0.15f,0.7f,32);

								 // left foot
								 GL11.glColor3f(blue[0], blue[1], blue[2]);
								 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
								 GL11.glPushMatrix(); {

									 //set the position of the left foot
									 GL11.glTranslatef(0.0f,0.0f,0.75f);

									 //draw the left foot
									 sphere.DrawSphere(0.3f, 32, 32);

								 } GL11.glPopMatrix();
							 } GL11.glPopMatrix();
						 } GL11.glPopMatrix();
					 } GL11.glPopMatrix();
				 } GL11.glPopMatrix();


				 //*******************************	Left parts of the leg (end)
				 //************************************************************************************************************************************************************


			 } GL11.glPopMatrix();

			 }
		 }else if ( task.equals("walk")){
			 GL11.glPushMatrix();

			 {
				 GL11.glTranslatef(0.0f,0.5f,0.0f);

				 // *** for test *** (make it start with a left side view) *** This should be commented when running the program
//			 GL11.glRotatef(-90, 0.0f, 1.0f, 0.0f);
				 // *** for test *** (make it start with a right side view) *** This should be commented when running the program
//			 GL11.glRotatef(90, 0.0f, 1.0f, 0.0f);

				 GL11.glRotatef(180, 0.0f, 1.0f, 0.0f);

				 // rotate the pelvis when running or walking
				 GL11.glRotatef(pelvisRotation, 0.0f, 0.1f, 0.0f);

				 // the whole body should fluctuate when walking or running (up and down)
				 GL11.glTranslatef(0.0f, bodyFluctuate, 0.0f);

				 //draw the pelvis
				 sphere.DrawSphere(0.5f, 32, 32);

				 /** draw shadow using the squeezed textured cube */
//				 GL11.glPushMatrix();{
//				 GL11.glTranslatef(10, -1.7f, 0);
//				 GL11.glScalef(8f, 0f, 2f);
//				 GL11.glTexParameteri(
//						 GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
//						 GL11.GL_CLAMP);
//				 Color.white.bind();
//				 textureShadow.bind();
//				 GL11.glEnable(GL11.GL_TEXTURE_2D);
//				 GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
//				 GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
//				 GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
//				 GL11.glEnable(GL11.GL_BLEND);
//				 texCube.DrawTexCube();
//
//				 GL11.glPopMatrix();
//			 }

				 //  chest
				 GL11.glColor3f(green[0], green[1], green[2]);
				 GL11.glMaterial(  GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(green));
				 GL11.glPushMatrix(); {
				 GL11.glTranslatef(0.0f,0.5f,0.0f);

				 // rotating the chest when running or walking
				 GL11.glRotatef(chestRotation, 0.0f, 1.0f, 0.0f);

				 /** use the texSphere to draw the human chest **/
				 /*
				  * This will show a Google sign at the side of the body
				  */
				 GL11.glScalef(1f, 1f,  1f);
				 GL11.glTexParameteri(
						 GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
						 GL11.GL_CLAMP);
				 Color.white.bind();
				 textureChest.bind();
				 GL11.glEnable(GL11.GL_TEXTURE_2D);
				 GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
				 GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
				 texSphere.DrawTexSphere(0.5f, 32, 32, textureChest);


				 // neck
				 GL11.glColor3f(orange[0], orange[1], orange[2]);
				 GL11.glMaterial( GL11.GL_FRONT,  GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
				 GL11.glPushMatrix(); {
					 GL11.glTranslatef(0.0f,0.0f, 0.0f);
					 GL11.glRotatef(-90.0f,1.0f,0.0f,0.0f);
					 //                    GL11.glRotatef(45.0f,0.0f,1.0f,0.0f);
					 cylinder.DrawCylinder(0.15f,0.7f,32);


					 // head
					 GL11.glColor3f(red[0], red[1], red[2]);
					 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(red));
					 GL11.glPushMatrix(); {
						 GL11.glTranslatef(0.0f,0.0f,1.0f);

						 /** use the texSphere to draw the human head **/
						 /*
						  * this will show a smiling on the face
						  */
						 GL11.glScalef(1f, 1f,  1f);
						 GL11.glRotatef(180, 1.0f, 0.0f, 0.0f);
						 GL11.glRotatef(-90, 0.0f, 0.0f, 1.0f);
						 GL11.glTexParameteri(
								 GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
								 GL11.GL_CLAMP);
						 Color.white.bind();
						 textureHead.bind();
						 GL11.glEnable(GL11.GL_TEXTURE_2D);
						 GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
						 GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
						 texSphere.DrawTexSphere(0.5f, 32, 32, textureHead);

						 GL11.glPopMatrix();
					 } GL11.glPopMatrix();


					 // left shoulder
					 GL11.glColor3f(blue[0],blue[1], blue[2]);
					 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
					 GL11.glPushMatrix(); {
						 GL11.glTranslatef(0.5f,0.4f,0.0f);
						 sphere.DrawSphere(0.25f, 32, 32);


						 // left arm
						 GL11.glColor3f(orange[0], orange[1], orange[2]);
						 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
						 GL11.glPushMatrix(); {
							 GL11.glTranslatef(0.0f,0.0f,0.0f);
							 GL11.glRotatef(90.0f,1.0f,0.3f,0.0f);	// when running, elbow should be a little bit outward

							 // rotate the left arm when walking
							 GL11.glRotatef(leftArmRotation,1.0f,0.0f,0.0f);
							 cylinder.DrawCylinder(0.15f,0.7f,32);


							 // left elbow
							 GL11.glColor3f(blue[0], blue[1], blue[2]);
							 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
							 GL11.glPushMatrix(); {
								 GL11.glTranslatef(0.0f,0.0f,0.75f);
								 sphere.DrawSphere(0.2f, 32, 32);

								 //left forearm
								 GL11.glColor3f(orange[0], orange[1], orange[2]);
								 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
								 GL11.glPushMatrix(); {
									 GL11.glTranslatef(0.0f,0.0f,0.0f);
									 //lift the left forearm up
									 GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);

									 // rotate the left forearm when walking or running
									 GL11.glRotatef(leftForeArmRotation,1.0f,0.0f,0.0f);
									 cylinder.DrawCylinder(0.1f,0.7f,32);

									 // left hand
									 GL11.glColor3f(blue[0], blue[1], blue[2]);
									 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
									 GL11.glPushMatrix(); {
										 GL11.glTranslatef(0.0f,0.0f,0.75f);
										 sphere.DrawSphere(0.2f, 32, 32);



									 } GL11.glPopMatrix();
								 } GL11.glPopMatrix();
							 } GL11.glPopMatrix();
						 } GL11.glPopMatrix ();
					 } GL11.glPopMatrix ();
					 // to chest

					 //************************************************************************************************************************************************************
					 //******************************* Right parts of the arm (start)
					 // right shoulder
					 GL11.glColor3f(blue[0],blue[1], blue[2]);
					 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
					 GL11.glPushMatrix(); {
						 //set the position of the right shoulder
						 GL11.glTranslatef(-0.5f,0.4f,0.0f);
						 //draw right shoulder
						 sphere.DrawSphere(0.25f, 32, 32);


						 // right arm
						 GL11.glColor3f(orange[0], orange[1], orange[2]);
						 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
						 GL11.glPushMatrix(); {

							 //set the position of the right arm
							 GL11.glTranslatef(0.0f,0.0f,0.0f);

							 // when running, elbow should be a little bit outward
							 GL11.glRotatef(90.0f,1.0f,-0.3f,0.0f);

							 // right arm should keep moving when walking or running
							 GL11.glRotatef(rightArmRotation,1.0f,0.0f,0.0f);

							 //draw right arm
							 cylinder.DrawCylinder(0.15f,0.7f,32);


							 // right elbow
							 GL11.glColor3f(blue[0], blue[1], blue[2]);
							 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
							 GL11.glPushMatrix(); {

								 //set the position of the right elbow
								 GL11.glTranslatef(0.0f,0.0f,0.75f);

								 //draw right elbow
								 sphere.DrawSphere(0.2f, 32, 32);

								 //right forearm
								 GL11.glColor3f(orange[0], orange[1], orange[2]);
								 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
								 GL11.glPushMatrix(); {

									 //set the position of the left forearm
									 GL11.glTranslatef(0.0f,0.0f,0.0f);

									 // lift the right forearm up
									 GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);

									 // rotate the right forearm when walking or running
									 GL11.glRotatef(rightForeArmRotation,1.0f,0.0f,0.0f);

									 //draw the right forearm
									 cylinder.DrawCylinder(0.1f,0.7f,32);

									 // right hand
									 GL11.glColor3f(blue[0], blue[1], blue[2]);
									 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
									 GL11.glPushMatrix(); {

										 //set the position of the right hand
										 GL11.glTranslatef(0.0f,0.0f,0.75f);

										 //draw the right hand
										 sphere.DrawSphere(0.2f, 32, 32);



									 } GL11.glPopMatrix();
								 } GL11.glPopMatrix();
							 } GL11.glPopMatrix();
						 } GL11.glPopMatrix ();
					 } GL11.glPopMatrix ();

					 //******************************* Right parts of the arm (end)
					 //************************************************************************************************************************************************************


					 //chest


				 } GL11.glPopMatrix();


				 // pelvis


				 // right hip
				 GL11.glColor3f(blue[0], blue[1], blue[2]);
				 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
				 GL11.glPushMatrix(); {
					 GL11.glTranslatef(-0.5f,-0.2f,0.0f);

					 sphere.DrawSphere(0.25f, 32, 32);


					 // right high leg
					 GL11.glColor3f(orange[0], orange[1], orange[2]);
					 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
					 GL11.glPushMatrix(); {
						 GL11.glTranslatef(0.0f,0.0f,0.0f);
						 GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);


						 // rotate the right high leg when walking or running
						 GL11.glRotatef(rightHighLegRotation,1.0f,0.0f,0.0f);
						 cylinder.DrawCylinder(0.15f,0.7f,32);


						 // right knee
						 GL11.glColor3f(blue[0], blue[1], blue[2]);
						 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
						 GL11.glPushMatrix(); {
							 GL11.glTranslatef(0.0f,0.0f,0.75f);
							 GL11.glRotatef(0.0f,0.0f,0.0f,0.0f);
							 sphere.DrawSphere(0.25f, 32, 32);

							 //right low leg
							 GL11.glColor3f(orange[0], orange[1], orange[2]);
							 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
							 GL11.glPushMatrix(); {
								 GL11.glTranslatef(0.0f,0.0f,0.0f);

								 // rotate the right low leg when walking or running
								 GL11.glRotatef(rightLowLegRotation, 1.0f, 0.0f, 0.0f);
								 cylinder.DrawCylinder(0.15f,0.7f,32);

								 // right foot
								 GL11.glColor3f(blue[0], blue[1], blue[2]);
								 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
								 GL11.glPushMatrix(); {
									 GL11.glTranslatef(0.0f,0.0f,0.75f);
									 sphere.DrawSphere(0.3f, 32, 32);

								 } GL11.glPopMatrix();
							 } GL11.glPopMatrix();
						 } GL11.glPopMatrix();
					 } GL11.glPopMatrix();
				 } GL11.glPopMatrix();

				 // pelvis


				 //************************************************************************************************************************************************************
				 //*******************************  Left parts of the leg (start)
				 // left hip
				 GL11.glColor3f(blue[0], blue[1], blue[2]);
				 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
				 GL11.glPushMatrix(); {

					 //set the position of the left hip
					 GL11.glTranslatef(0.5f,-0.2f,0.0f);

					 //draw the left hip
					 sphere.DrawSphere(0.25f, 32, 32);


					 // left high leg
					 GL11.glColor3f(orange[0], orange[1], orange[2]);
					 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
					 GL11.glPushMatrix(); {

						 //set the position of the left high leg
						 GL11.glTranslatef(0.0f,0.0f,0.0f);

						 //draw the left high leg
						 GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);

						 // rotate the left high leg when walking or running
						 GL11.glRotatef(leftHighLegRotation,1.0f,0.0f,0.0f);

						 //draw the right high leg
						 cylinder.DrawCylinder(0.15f,0.7f,32);


						 // left knee
						 GL11.glColor3f(blue[0], blue[1], blue[2]);
						 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
						 GL11.glPushMatrix(); {

							 //set the position of the left knee
							 GL11.glTranslatef(0.0f,0.0f,0.75f);
							 GL11.glRotatef(0.0f,0.0f,0.0f,0.0f);

							 //draw the left knee
							 sphere.DrawSphere(0.25f, 32, 32);

							 //left low leg
							 GL11.glColor3f(orange[0], orange[1], orange[2]);
							 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(orange));
							 GL11.glPushMatrix(); {

								 //set the position of the left low leg
								 GL11.glTranslatef(0.0f,0.0f,0.0f);

								 // rotate the left low leg when walking or running
								 GL11.glRotatef(leftLowLegRotation,1.0f,0.0f,0.0f);

								 //draw the left low leg
								 cylinder.DrawCylinder(0.15f,0.7f,32);

								 // left foot
								 GL11.glColor3f(blue[0], blue[1], blue[2]);
								 GL11.glMaterial( GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,  Utils.ConvertForGL(blue));
								 GL11.glPushMatrix(); {

									 //set the position of the left foot
									 GL11.glTranslatef(0.0f,0.0f,0.75f);

									 //draw the left foot
									 sphere.DrawSphere(0.3f, 32, 32);

								 } GL11.glPopMatrix();
							 } GL11.glPopMatrix();
						 } GL11.glPopMatrix();
					 } GL11.glPopMatrix();
				 } GL11.glPopMatrix();


				 //*******************************	Left parts of the leg (end)
				 //************************************************************************************************************************************************************


			 } GL11.glPopMatrix();

			 }
		 }else if (task.equals("")){

		 }else if (task.equals("")){

		 }


		

	}
	
	
	
}
 
	/*
	 
	 
}

	*/
	 