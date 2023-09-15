package GraphicsObjects;



public class Vector4f {

	public float x=0;
	public float y=0;
	public float z=0;
	public float a=0;
	
	public Vector4f() 
	{  
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		a = 0.0f;
	}
	 
	public Vector4f(float x, float y, float z,float a) 
	{ 
		this.x = x;
		this.y = y;
		this.z = z;
		this.a = a;
	}
	
	 //implement Vector plus a Vector 
	public Vector4f PlusVector(Vector4f Additonal) 
	{ 
		return new Vector4f(this.x+Additonal.x, this.y+Additonal.y, this.z+Additonal.z,this.a+Additonal.a);
	}

	// implement Vector minus a Vector
	/**
	 * This is a method for calculating the result of vector minus a vector
	 * e.g. v1.MinusVector(v2) means v1 - v2
	 * @param Minus a vector that will be subtracted by this vector
	 * @return An object of Vector4f, which is a result vector of this minus operation
	 */
	public Vector4f MinusVector(Vector4f Minus) {
		//to Minus a vector, which has the same effect of plussing the negative Vector of the original one.
		return this.PlusVector(Minus.NegateVector());
	}

	// implement Vector plus a Point
	/**
	 * This is a method for calculating the result of vector plus a point
	 * @param Additional a point that will be added with the vector
	 * @return An object of Point4f, which is a result point of this add operation
	 */
	public Point4f PlusPoint(Point4f Additional) {
		//we get the sum of x in this vector and the x in the additional point to be the new x, similar for y and z and a
		//then generate a new point using the new x, y, z, a
		return new Point4f(this.x + Additional.x, this.y + Additional.y, this.z + Additional.z, this.a + Additional.a);
	}

	//Do not implement Vector minus a Point as it is undefined 

	// Implement a Vector * Scalar
	/**
	 * This is a method for calculating the result of scalar multiplication
	 * @param scale a number that will be used to multiply the vector
	 * @return  an object of Vector4f, which is a result of this scalar multiplication
	 */
	public Vector4f byScalar(float scale) {
		//multiply each dimension by scale then we get x, y, z, a after that generate an object of Vector4f using the x, y, z, a we get.
		//then return this object
		return new Vector4f(this.x * scale, this.y * scale, this.z * scale, this.a * scale);
	}

	// implement returning the negative of a Vector
	/**
	 * This method calculates the negative vector of the one calls it
	 * @return the negative vector of the one calls it
	 */
	public Vector4f NegateVector() {
		//scalar multiply the original vector by -1 then we get the negative vector of it
		return this.byScalar(-1);
	}
	
	//implement getting the length of a Vector  
	public float length()
	{
	    return (float) Math.sqrt(x*x + y*y + z*z+ a*a);
	}
	
	//Just to avoid confusion here is getting the Normal  of a Vector  
	public Vector4f Normal()
	{
		float LengthOfTheVector=  this.length();
		return this.byScalar(1.0f/ LengthOfTheVector); 
	}

	// implement getting the dot product of Vector.Vector
	/**
	 * This is a method for calculating the dot product of this vector and a given vector
	 * (a.dot(b) == b.dot(a))
	 * @param v a vector that well be used to calculate the dot product with the vector that calls this method
	 * @return a scalar that is the result of the dot product of this vector and the given vector
	 */
	public float dot(Vector4f v) {
		//calculate the scalar product of these two vectors on each dimension then add them up
		return (this.x * v.x + this.y * v.y + this.z * v.z + this.a * v.a);
	}
	
	// Implemented this for you to avoid confusion 
	// as we will not normally  be using 4 float vector  
	public Vector4f cross(Vector4f v)  
	{ 
    float u0 = (this.y*v.z - z*v.y);
    float u1 = (z*v.x - x*v.z);
    float u2 = (x*v.y - y*v.x);
    float u3 = 0; //ignoring this for now  
    return new Vector4f(u0,u1,u2,u3);
	}
 
}
	 
	   

/*

										MMMM                                        
										MMMMMM                                      
 										MM MMMM                                    
 										MMI  MMMM                                  
 										MMM    MMMM                                
 										MMM      MMMM                              
  										MM        MMMMM                           
  										MMM         MMMMM                         
  										MMM           OMMMM                       
   										MM             .MMMM                     
MMMMMMMMMMMMMMM                        MMM              .MMMM                   
MM   IMMMMMMMMMMMMMMMMMMMMMMMM         MMM                 MMMM                 
MM                  ~MMMMMMMMMMMMMMMMMMMMM                   MMMM               
MM                                  OMMMMM                     MMMMM            
MM                                                               MMMMM          
MM                                                                 MMMMM        
MM                                                                   ~MMMM      
MM                                                                     =MMMM    
MM                                                                        MMMM  
MM                                                                       MMMMMM 
MM                                                                     MMMMMMMM 
MM                                                                  :MMMMMMMM   
MM                                                                MMMMMMMMM     
MM                                                              MMMMMMMMM       
MM                             ,MMMMMMMMMM                    MMMMMMMMM         
MM              IMMMMMMMMMMMMMMMMMMMMMMMMM                  MMMMMMMM            
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM               ZMMMMMMMM              
MMMMMMMMMMMMMMMMMMMMMMMMMMMMM          MM$             MMMMMMMMM                
MMMMMMMMMMMMMM                       MMM            MMMMMMMMM                  
  									MMM          MMMMMMMM                     
  									MM~       IMMMMMMMM                       
  									MM      DMMMMMMMM                         
 								MMM    MMMMMMMMM                           
 								MMD  MMMMMMMM                              
								MMM MMMMMMMM                                
								MMMMMMMMMM                                  
								MMMMMMMM                                    
  								MMMM                                      
  								MM                                        
                             GlassGiant.com */