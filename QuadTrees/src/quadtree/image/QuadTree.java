/**
 * 
 */
package quadtree.image;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author SAMSUNG
 *
 */

public class QuadTree {
    
	
	    int width = 0; 
	    int height = 0; 
	    Pixel arr[][]; 
	    Pixel arrAux[][]; 
	    Scanner in = null;
	    Node root;

	    public void processFile(String fileName) { // reads image file and stores each pixel in a pixel array.
	        try {
	            in = new Scanner(new FileInputStream(fileName));
	        } catch (FileNotFoundException e) {
	            System.out.println(e.getMessage());
	        }
	        in.next(); 
	        width = Integer.parseInt(in.next());
	        height = Integer.parseInt(in.next());
	        arr = new Pixel[width][height];
	        arrAux = new Pixel[width][height];
	        in.next(); 

	        while (in.hasNext()) { 
	            for (int w = 0; w < width; w++) {
	                for (int h = 0; h < height; h++) {
	                    int a = in.nextInt();
	                    int b = in.nextInt();
	                    int c = in.nextInt();
	                    Pixel p1 = new Pixel(a, b, c);
	                    arr[w][h] = p1;
	                    Pixel p2 = new Pixel(a,b,c);
	                    arrAux[w][h] = p2;
	                }
	            }
	        }

//	        for(int x = 0; x < width; x++){
//	            for(int y = 0; y < height; y++){
//	                System.out.print("[" + x + "," + y + "]" + "rgb" + "(" +  arr[x][y].red + ", " + + arr[x][y].green + ", " + arr[x][y].blue + ") " );
//	            }
//	            System.out.println();
//	        }
	    }

	    public static class Node {
	        int i1, i2, j1, j2; 
	        Pixel meanColor = new Pixel(0, 0, 0);
	        double meanSquaredError = 0.0;

	        String element = null; 
	        Node NE = null;
	        Node NW = null;
	        Node SW = null;
	        Node SE = null;
	        Node parent = null;

	        public void setCoordinats(int i1, int j1, int i2, int j2) { 
	            this.i1 = i1;
	            this.i2 = i2;
	            this.j1 = j1;
	            this.j2 = j2;
	        }
	    } 
        //preorder tree traversal
	    public void preorderSubtree(Node n, List<Node> snapshot) {
	        snapshot.add(n);                       // for preorder, we add position p before exploring subtrees
	        for (Node c : children(n))
	            preorderSubtree(c, snapshot);
	    }
        
	    public Iterable<Node> preorder() { 
	        List<Node> snapshot = new ArrayList<>();
	       
	        preorderSubtree(root, snapshot);   
	        return snapshot;
	    }
        //print nodes
	    public void printNodes(Iterable<Node> list) { 
	        for (Node n : list) {
	            //System.out.println(n.element + " " + n.i1 + "," + n.j1 + ":" + n.i2 + "," + n.j2 + " ");
//	            calculateMeanColor(n);
//	            calculateMeanSquaredError(n);
//	            System.out.println(n.element + " " + n.meanSquaredError);
	        }
	    }
        
	    public Iterable<Node> children(Node n) { 
	        List<Node> snapshot = new ArrayList<>(4);  
	        if (n.NE != null) {
	            snapshot.add(n.NE);
	            snapshot.add(n.NW);
	            snapshot.add(n.SW);
	            snapshot.add(n.SE);
	        }
	        return snapshot;
	    }
        // Changing original image to negative
	    public void changeNegative() { 
	        PrintWriter p1 = null;
	        try {
	            p1 = new PrintWriter(new FileOutputStream("images123.ppm"));
	        } catch (FileNotFoundException e) {
	            System.out.println(e.getMessage());
	        }

	        p1.write("P3\n");
	        p1.write(width + " ");
	        p1.write(height + "\n");
	        p1.write("255" + "\n");

	        for (int w = 0; w < width; w++) {
	            for (int h = 0; h < height; h++) {
	                int newRed = 255 - arr[w][h].red;
	                int newBlue = 255 - arr[w][h].blue;
	                int newGreen = 255 - arr[w][h].green;
	                p1.write("" + newRed + " ");
	                p1.write("" + newGreen + " ");
	                p1.write("" + newBlue + " ");
	            }
	            p1.write("\n");
	        }
	        p1.close();
	    }
        //Changing original image to grey based image
	    public void changeGreyScale() { 
	        PrintWriter p1 = null;
	        try {
	            p1 = new PrintWriter(new FileOutputStream("flowers.ppm"));
	        } catch (FileNotFoundException e) {
	            System.out.println(e.getMessage());
	        }

	        p1.write("P3\n");
	        p1.write(width + " ");
	        p1.write(height + "\n");
	        p1.write("255" + "\n");

	        for (int w = 0; w < width; w++) {
	            for (int h = 0; h < height; h++) {
	                int newRed = (int) (arr[w][h].red * 0.3);
	                int newBlue = (int) (arr[w][h].blue * 0.11);
	                int newGreen = (int) (arr[w][h].green * 0.59);
	                int newColor = newRed + newGreen + newBlue;
	                p1.write("" + newColor + " ");
	                p1.write("" + newColor + " ");
	                p1.write("" + newColor + " ");
	            }
	            p1.write("\n");
	        }
	        p1.close();
	    }
        //changing image color to a different colors
	    public void changeTint(int R, int G, int B) { 
	        PrintWriter p1 = null;
	        try {
	            p1 = new PrintWriter(new FileOutputStream("spring.ppm"));
	        } catch (FileNotFoundException e) {
	            System.out.println(e.getMessage());
	        }

	        p1.write("P3\n");
	        p1.write(width + " ");
	        p1.write(height + "\n");
	        p1.write("255" + "\n");

	        for (int w = 0; w < width; w++) {
	            for (int h = 0; h < height; h++) {
	                int newRed = (int) ((double) (arr[w][h].red) / 255 * R);
	                int newBlue = (int) ((double) (arr[w][h].blue) / 255 * B);
	                int newGreen = (int) ((double) (arr[w][h].green) / 255 * G);
	                p1.write(+newRed + " ");
	                p1.write(+newGreen + " ");
	                p1.write(+newBlue + " ");
	            }
	            p1.write("\n");
	        }
	        p1.close();
	    }
        // creating root
	    public Node createRoot() { 
	        root = new Node();
	        root.parent = null;
	        root.i1 = 0;
	        root.i2 = width - 1;
	        root.j1 = 0;
	        root.j2 = height - 1;
	        root.element = "ROOT";
	        calculateMeanColor(root);
	        calculateMeanSquaredError(root);
	        return root;
	    }
        // dividing n to 4 leaf nodes
	    public void quadDivision(Node n) { 
	        Node nodeNE = new Node();
	        nodeNE.parent = n;
	        n.NE = nodeNE;
	        nodeNE.element = "NE";
	        nodeNE.setCoordinats(n.i1, (n.j1 + n.j2) / 2 + 1, (n.i1 + n.i2) / 2, n.j2);
	        calculateMeanColor(nodeNE);
	        calculateMeanSquaredError(nodeNE);

	        for (int i = nodeNE.i1; i < nodeNE.i2; i++) { 
	            for (int j = nodeNE.j1; j < nodeNE.j2; j++) {
	                arrAux[i][j].red = nodeNE.meanColor.red;
	                arrAux[i][j].green = nodeNE.meanColor.green;
	                arrAux[i][j].blue = nodeNE.meanColor.blue;
	            }
	        }

	        Node nodeNW = new Node();
	        nodeNW.parent = n;
	        n.NW = nodeNW;
	        nodeNW.element = "NW";
	        nodeNW.setCoordinats(n.i1, n.j1, (n.i1 + n.i2) / 2, (n.j1 + n.j2) / 2);
	        calculateMeanColor(nodeNW);
	        calculateMeanSquaredError(nodeNW);

	        for (int i = nodeNW.i1; i < nodeNW.i2; i++) { // assigns mean color of the divided region to new array of pixels.
	            for (int j = nodeNW.j1; j < nodeNW.j2; j++) {
	                arrAux[i][j].red = nodeNW.meanColor.red;
	                arrAux[i][j].green = nodeNW.meanColor.green;
	                arrAux[i][j].blue = nodeNW.meanColor.blue;
	            }
	        }


	        Node nodeSW = new Node();
	        nodeSW.parent = n;
	        n.SW = nodeSW;
	        nodeSW.element = "SW";
	        nodeSW.setCoordinats((n.i1 + n.i2) / 2 + 1, n.j1, n.i2, (n.j1 + n.j2) / 2);
	        calculateMeanColor(nodeSW);
	        calculateMeanSquaredError(nodeSW);

	        for (int i = nodeSW.i1; i < nodeSW.i2; i++) { 
	            for (int j = nodeSW.j1; j < nodeSW.j2; j++) {
	                arrAux[i][j].red = nodeSW.meanColor.red;
	                arrAux[i][j].green = nodeSW.meanColor.green;
	                arrAux[i][j].blue = nodeSW.meanColor.blue;
	            }
	        }


	        Node nodeSE = new Node();
	        nodeSE.parent = n;
	        n.SE = nodeSE;
	        nodeSE.element = "SE";
	        nodeSE.setCoordinats((n.i1 + n.i2) / 2 + 1, (n.j1 + n.j2) / 2 + 1, n.i2, n.j2);
	        calculateMeanColor(nodeSE);
	        calculateMeanSquaredError(nodeSE);

	        for (int i = nodeSE.i1; i < nodeSE.i2; i++) { 
	            for (int j = nodeSE.j1; j < nodeSE.j2; j++) {
	                arrAux[i][j].red = nodeSE.meanColor.red;
	                arrAux[i][j].green = nodeSE.meanColor.green;
	                arrAux[i][j].blue = nodeSE.meanColor.blue;
	            }
	        }
	    }

	    public boolean isExternal(Node n){ 
	        if(n.NE == null)
	            return true;
	        else
	            return false;
	    }


	    public void calculateMeanColor(Node n) {
	        calculateColor(n, n.i1, n.j1, n.i2, n.j2);
	    }
        //Calculate color
	    private void calculateColor(Node n, int i1, int j1, int i2, int j2) {
	        int sumRed = 0;
	        int sumGreen = 0;
	        int sumBlue = 0;
	        int averageRed = 0;
	        int averageGreen = 0;
	        int averageBlue = 0;

	        int area = (i2-i1)*(j2-j1);
	        int i, j = 0;
	        for (i = i1; i < i2; i++) {
	            for (j = j1; j < j2; j++) {
	                sumRed += arr[i][j].red;
	                sumGreen += arr[i][j].green;
	                sumBlue += arr[i][j].blue;
	            }
	        }

	        averageRed = sumRed / area;
	        averageGreen = sumGreen / area;
	        averageBlue = sumBlue / area;

	        n.meanColor.red = averageRed;
	        n.meanColor.green = averageGreen;
	        n.meanColor.blue = averageBlue;
	    }

	    public double calculateMeanSquaredError(Node n) {
	        return calculateError(n, n.i1, n.j1, n.i2, n.j2);
	    }

	    private double calculateError(Node n, int i1, int j1, int i2, int j2) { 
	        double errorPerPixel = 0.0;
	        double area = (i2-i1)*(j2-j1);
	        int i, j = 0;
	        for (i = i1; i < i2; i++) {
	            for (j = j1; j < j2; j++) {
	                errorPerPixel += (double) Math.pow(arr[i][j].red - (n.meanColor.red), 2);
	                errorPerPixel += (double)Math.pow(arr[i][j].green - (n.meanColor.green), 2);
	                errorPerPixel += (double) Math.pow(arr[i][j].blue - (n.meanColor.blue), 2);;
	            }
	        }
	        n.meanSquaredError = (double) (errorPerPixel) / area;
	        return n.meanSquaredError;
	    }

	    public void edgeDetection(String input,String output) { 
	        QuadTree image = new QuadTree();
	        image.processFile(input);
	        PrintWriter p = null;
	        try {
	            p = new PrintWriter(new FileOutputStream(output + ".ppm"));
	        } catch (FileNotFoundException e) {
	            System.out.println(e.getMessage());
	        }

	        p.write("P3\n");
	        p.write(image.width + " ");
	        p.write(image.height + "\n");
	        p.write("255" + "\n");

	        for (int i = 0; i < width; i++) {
	            image.arr[0][i].red = 0;
	            image.arr[0][i].green = 0;
	            image.arr[0][i].blue = 0;
	        }
	        for (int i = 0; i < width; i++) {
	            image.arr[i][0].red = 0;
	            image.arr[i][0].green = 0;
	            image.arr[i][0].blue = 0;
	        }
	        for (int i = width - 1; i >= 0; i--) {
	            image.arr[0][i].red = 0;
	            image.arr[0][i].green = 0;
	            image.arr[0][i].blue = 0;
	        }
	        for (int i = 0; i < width; i++) {
	            image.arr[511][i].red = 0;
	            image.arr[511][i].green = 0;
	            image.arr[511][i].blue = 0;
	        }

	        int averageColor = 0;
	        int averageNeighborhoodColor = 0;
	        for (int i = 1; i < width - 1; i++) {
	            for (int j = 1; j < height - 1; j++) {
	                averageColor = calculateAverageColor(arr[i][j]);
	                averageNeighborhoodColor = (calculateAverageColor(arr[i - 1][j - 1]) + calculateAverageColor(arr[i][j + 1]) + calculateAverageColor(arr[i][j - 1]) +
	                        calculateAverageColor(arr[i - 1][j]) + calculateAverageColor(arr[i + 1][j]) + calculateAverageColor(arr[i + 1][j + 1]) +
	                        calculateAverageColor(arr[i - 1][j + 1]) + calculateAverageColor(arr[i + 1][j - 1])) / 8;
	                if (Math.abs(averageColor - averageNeighborhoodColor) > 5) {
	                    image.arr[i][j].red = 255;
	                    image.arr[i][j].green = 255;
	                    image.arr[i][j].blue = 255;
	                } else {
	                    image.arr[i][j].red = 0;
	                    image.arr[i][j].green = 0;
	                    image.arr[i][j].blue = 0;
	                }
	            }
	        }

	        for (int i = 0; i < width; i++) {
	            for (int j = 0; j < height; j++) {
	                p.write(image.arr[i][j].red + " ");
	                p.write(image.arr[i][j].green + " ");
	                p.write(image.arr[i][j].blue + " ");
	            }
	            p.write("\n");
	        }
	        p.close();
	    }

	    private int calculateAverageColor(Pixel arr) {
	        return (arr.red + arr.green + arr.blue) / 3;
	    }

	    private boolean ableToDivide(Node n, double threshold) {

	        return n.meanSquaredError > threshold;
	    }

	    public boolean compressCheck(Node n, double threshold) { 
	        boolean x = false;
	        Iterable<Node> list = preorder();
	        for (Node c : list) {
	            if(isExternal(c)) {
	                if (ableToDivide(c, threshold)) {
	                    x = true;
	                }
	            }
	        }
	        if(x)
	            return true;
	        else
	            return false;
	    }

	    int count = 0; 
	    public void compress(Node n,double threshold) { // the compression method.
	        while (compressCheck(n, threshold)) { 
	            Iterable<Node> list = preorder();
	            for (Node c : list) {
	                if(isExternal(c)) { 
	                    if (ableToDivide(c, threshold)) { 
	                        quadDivision(c);
	                        count++;
	                    }
	                }
	            }
	        }
	        System.out.println("count of divisions: " + count);
	    }
	    public void writeFile(String outputFile) { 
	        PrintWriter p1 = null;
	        try {
	            p1 = new PrintWriter(new FileOutputStream(outputFile + ".ppm"));
	        } catch (FileNotFoundException e) {
	            System.out.println(e.getMessage());
	        }

	        p1.write("P3\n");
	        p1.write(width + " ");
	        p1.write(height + "\n");
	        p1.write("255" + "\n");

	        for (int w = 0; w < width; w++) {
	            for (int h = 0; h < height; h++) {
	                int newRed =  arrAux[w][h].red;
	                int newBlue =  arrAux[w][h].blue;
	                int newGreen =  arrAux[w][h].green;
	                p1.write( newRed + " ");
	                p1.write( newGreen + " ");
	                p1.write( newBlue + " ");
	            }
	            p1.write("\n");
	        }
	        p1.close();
	    }
	}	
	

