/**
 * 
 */
package quadtree.image;



/**
 * @author SAMSUNG
 *
 */
public class Main {
     public static void main(String args[]) {
	        String inputFile = "";
	        String outputFile = "";
	        QuadTree image = new QuadTree();
	      //finds the name of the input and output files.
	        for(int i = 0; i < args.length; i++){ 
	            if(args[i].equals("-i"))
	                inputFile = args[i+1];
	            if(args[i].equals("-o"))
	                outputFile = args[i+1];
	        }

	        image.processFile(inputFile);
	        for(int i = 0; i < args.length; i++){
	            if(args[i].equals("-c")){ 
	                image.compress(image.createRoot(),0.002);
	                image.writeFile(outputFile + "-1");

	                image.compress(image.createRoot(),0.004);
	                image.writeFile(outputFile + "-2");

	                image.compress(image.createRoot(),0.01);
	                image.writeFile(outputFile + "-3");

	                image.compress(image.createRoot(),0.033);
	                image.writeFile(outputFile + "-4");

	                image.compress(image.createRoot(),0.077);
	                image.writeFile(outputFile + "-5");

	                image.compress(image.createRoot(),0.2);
	                image.writeFile(outputFile + "-6");

	                image.compress(image.createRoot(),0.5);
	                image.writeFile(outputFile + "-7");

	                image.compress(image.createRoot(),0.65);
	                image.writeFile(outputFile + "-8");
	            }
	            else if(args[i].equals("-e")){ 
	                image.edgeDetection(inputFile,outputFile);
	            }
	        }

	        image.changeGreyScale();
	        image.changeNegative();
	        image.changeTint(100,0,0);
	    }
	}	
	

