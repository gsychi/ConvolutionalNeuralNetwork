package PROCESSING;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ConvolutionConstruction {

	//define kernels
		static int[][] EDGE_DETECT_3 = {{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
		static int[][] ENHANCE = {{0,0,0},{-1,1,0},{0,0,0}};
		static int[][] EDGE_DETECT_1 = {{1,0,-1},{0,0,0},{-1,0,1}};
		static int[][] EDGE_DETECT_2 = {{0,1,0},{1,-4,1},{0,1,0}};
		static int[][] BOTTOM_SOBEL = {{-1,-2,-1},{0,0,0},{1,2,1}};
		static int[][] TOP_SOBEL = {{1,2,1},{0,0,0},{-1,-2,-1}};
		static int[][] LEFT_SOBEL = {{1,0,-1},{2,0,-2},{1,0,-1}};
		static int[][] RIGHT_SOBEL = {{-1,0,1},{-2,0,2},{-1,0,1}};
		static int[][] COPY = {{0,0,0},{0,1,0},{0,0,0}};
		static int[][] SHIFT_LEFT = {{0,0,0},{1,0,0},{0,0,0}};

		//In this area, you can change the construction of the Image
		public static double[] processed(BufferedImage image) {

			int width = image.getWidth();
			int height = image.getHeight();
			int[][] INPUT_RED = new int[height][width];
			int[][] INPUT_BLUE = new int[height][width];
			int[][] INPUT_GREEN = new int[height][width];

			for (int row = 0; row < height; row++) {
				for (int col = 0; col < width; col++) {
					Color colour = new Color(image.getRGB(col, row));
					int red = colour.getRed();
					int green = colour.getGreen();
					int blue = colour.getBlue();
					INPUT_RED[row][col] = red;
					INPUT_BLUE[row][col] = blue;
					INPUT_GREEN[row][col] = green;
				}
			}
			
			//FIRST LAYER
			FeatureMap a = new FeatureMap(INPUT_RED); //YOU CAN DETERMINE THE PARENT OF THE FEATURE MAP
			a.applyConvolution(TOP_SOBEL);

			FeatureMap b = new FeatureMap(INPUT_RED); 
			b.applyConvolution(EDGE_DETECT_2);
			
			FeatureMap c = new FeatureMap(INPUT_BLUE); 
			c.applyConvolution(RIGHT_SOBEL);
			
			FeatureMap d = new FeatureMap(INPUT_BLUE); 
			d.applyConvolution(EDGE_DETECT_2);
			
			FeatureMap e = new FeatureMap(INPUT_GREEN); 
			e.applyConvolution(BOTTOM_SOBEL);
			
			FeatureMap f = new FeatureMap(INPUT_GREEN); 
			f.applyConvolution(EDGE_DETECT_2);

			//SECOND LAYER: POOL EACH ONE
			int poolingWidth = 4;
			int poolingHeight = 4;

			FeatureMap a1 = new FeatureMap(a.processedPhoto);
			a1.averagePooling(poolingWidth, poolingHeight); //width * height

			FeatureMap b1 = new FeatureMap(b.processedPhoto);
			b1.averagePooling(poolingWidth, poolingHeight);

			FeatureMap c1 = new FeatureMap(c.processedPhoto);
			c1.averagePooling(poolingWidth, poolingHeight);
			
			FeatureMap d1 = new FeatureMap(d.processedPhoto);
			d1.averagePooling(poolingWidth, poolingHeight);
			
			FeatureMap e1 = new FeatureMap(e.processedPhoto);
			e1.averagePooling(poolingWidth, poolingHeight);
			
			FeatureMap f1 = new FeatureMap(f.processedPhoto);
			f1.averagePooling(poolingWidth, poolingHeight);
			
			//THIRD LAYER: CONVOLUTE AGAIN?
			FeatureMap a2 = new FeatureMap(a1.processedPhoto);
			a2.applyConvolution(EDGE_DETECT_2);
			
			FeatureMap b2 = new FeatureMap(b1.processedPhoto); 
			b2.applyConvolution(LEFT_SOBEL);
			
			FeatureMap c2 = new FeatureMap(c1.processedPhoto);
			c2.applyConvolution(EDGE_DETECT_2);
			
			FeatureMap d2 = new FeatureMap(d1.processedPhoto); 
			d2.applyConvolution(LEFT_SOBEL);
			
			FeatureMap e2 = new FeatureMap(e1.processedPhoto);
			e2.applyConvolution(EDGE_DETECT_2);
			
			FeatureMap f2 = new FeatureMap(f1.processedPhoto); 
			f2.applyConvolution(LEFT_SOBEL);

			//FOURTH LAYER: POOL AGAIN
			int poolingWidth2 = 2;
			int poolingHeight2 = 2;

			FeatureMap a3 = new FeatureMap(a2.processedPhoto);
			a3.maxPooling(poolingWidth2, poolingHeight2); //width * height

			FeatureMap b3 = new FeatureMap(b2.processedPhoto);
			b3.maxPooling(poolingWidth2, poolingHeight2);

			FeatureMap c3 = new FeatureMap(c2.processedPhoto);
			c3.maxPooling(poolingWidth2, poolingHeight2); 

			FeatureMap d3 = new FeatureMap(d2.processedPhoto);
			d3.maxPooling(poolingWidth2, poolingHeight2);
			
			FeatureMap e3 = new FeatureMap(e2.processedPhoto);
			e3.maxPooling(poolingWidth2, poolingHeight2); 

			FeatureMap f3 = new FeatureMap(f2.processedPhoto);
			f3.maxPooling(poolingWidth2, poolingHeight2);
			
			//fIFTH LAYER: POOL AGAIN
			int poolingWidth3 = 4;
			int poolingHeight3 = 4;

			FeatureMap a4 = new FeatureMap(a3.processedPhoto);
			a4.averagePooling(poolingWidth3, poolingHeight3); 

			FeatureMap b4 = new FeatureMap(b3.processedPhoto);
			b4.averagePooling(poolingWidth3, poolingHeight3);
			
			FeatureMap c4 = new FeatureMap(c3.processedPhoto);
			c4.averagePooling(poolingWidth3, poolingHeight3); 

			FeatureMap d4 = new FeatureMap(d3.processedPhoto);
			d4.averagePooling(poolingWidth3, poolingHeight3);
			
			FeatureMap e4 = new FeatureMap(e3.processedPhoto);
			e4.averagePooling(poolingWidth3, poolingHeight3); 

			FeatureMap f4 = new FeatureMap(f3.processedPhoto);
			f4.averagePooling(poolingWidth3, poolingHeight3);
			
			double[] imageProcessed = mxjava.connectDoubleArrays(mxjava.connectDoubleArrays(mxjava.connectArrays(mxjava.twoDtoOne(c4),mxjava.twoDtoOne(d4)), mxjava.connectArrays(mxjava.twoDtoOne(e4),mxjava.twoDtoOne(f4))), mxjava.connectArrays(mxjava.twoDtoOne(a4),mxjava.twoDtoOne(b4)));;

			//System.out.println(Arrays.toString(imageProcessed)+"\n-----");

			return imageProcessed;
		}

}
