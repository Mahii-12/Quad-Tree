/**
 * 
 */
package quadtree.image;

/**
 * @author SAMSUNG
 *
 */
public class Pixel {

	   
	    

	    int red  = 0;
	    int green = 0;
	    int blue = 0;

	    Pixel(int r, int g, int b) {
	        red = r;
	        green = g;
	        blue = b;
	    }

	    public String toString(){
	        return "(r: " + red + " g: " + green + " b: " + blue + ")";
	    }
	}	
	

