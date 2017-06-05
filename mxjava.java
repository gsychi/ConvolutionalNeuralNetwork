package PROCESSING;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class mxjava {
	
	public static BufferedImage retrievePhoto(String bank) {
		BufferedImage image = null;
		String directory = System.getProperty ("user.home") + "/Documents/photosForCNN/" + bank;
		File photo = new File(directory);
		try {
			image = ImageIO.read(photo);
			return image;
		}
		catch (IOException e) {
			System.out.println("Failed to retrieve photo.");
		}
		return null;
	}

	public static double sigmoidPackage(double x, boolean deriv) {
		if (deriv) {
			return x*(1-x);
		}
		return 1/(1+Math.pow(Math.E,-x));
	}

	public static double[][] synapseLayer(int inputs, int outputs) {
		double[][] LAYER = new double[inputs][outputs];
		for (int i = 0;i<inputs;i++) {
			for (int j = 0;j<outputs;j++) {
				LAYER[i][j] = (Math.random()*2)-1;
			}
		}
		return LAYER;
	}

	public static double[][] matrixMult(double[][] firstMatrix, double[][] secondMatrix) {
		double[][] newMatrix = new double[firstMatrix.length][secondMatrix[0].length];
		for (int i = 0; i < newMatrix.length; i++) { 
			for (int j = 0; j < newMatrix[0].length; j++) { 
				for (int k = 0; k < firstMatrix[0].length; k++) { 
					newMatrix[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
				}
			}
		}
		return newMatrix;
	}
	public static double[][] scalarMult(double[][]firstMatrix,double[][]secondMatrix) {
		double[][] newMatrix = new double[firstMatrix.length][secondMatrix[0].length];
		for (int i = 0; i < newMatrix.length; i++) { 
			for (int j = 0; j < newMatrix[0].length; j++) { 
				newMatrix[i][j] = firstMatrix[i][j]*secondMatrix[i][j];
			}
		}
		return newMatrix;
	}
	public static double[][] subtract(double[][] firstMatrix, double[][] secondMatrix) {
		double[][] result = new double[firstMatrix.length][firstMatrix[0].length];
		for (int i = 0; i < firstMatrix.length; i++) {
			for (int j = 0; j < firstMatrix[0].length; j++) {
				result[i][j] = firstMatrix[i][j] - secondMatrix[i][j];
			}
		}
		return result;
	}
	public static double[][] add(double[][] firstMatrix, double[][] secondMatrix) {
		double[][] result = new double[firstMatrix.length][firstMatrix[0].length];
		for (int i = 0; i < firstMatrix.length; i++) {
			for (int j = 0; j < firstMatrix[0].length; j++) {
				result[i][j] = firstMatrix[i][j] + secondMatrix[i][j];
			}
		}
		return result;
	}

	public static double[][] transpose(double [][] m){
		double[][] temp = new double[m[0].length][m.length];
		for (int i = 0; i < m.length; i++)
			for (int j = 0; j < m[0].length; j++)
				temp[j][i] = m[i][j];
		return temp;
	}

	public static void print(String x, double[][]m) {
		System.out.println(x);
		for (int i = 0;i<m.length;i++) {
			for (int j = 0;j<m[0].length;j++) {
				System.out.println(m[i][j]);
			}
			System.out.println("-----row done-----");
		}		
		System.out.println("\n");
	}
	
	//CNN SPECIFIC
	public static int convolution(int[][]rawPhoto,int[][] filter) {
		int newVal = 0;
		for (int i = 0; i < filter.length; i++) { 
			for (int j = 0; j < filter[0].length; j++) { 
				newVal += rawPhoto[i][j]*filter[filter.length-i-1][filter[0].length-j-1];
				//newVal += rawPhoto[i][j]*filter[i][j];
				
			}
		}
		return newVal;
	}
	
	public static int[][] randomFilter() {
		int[][] filter = new int[3][3];
		for (int i = 0;i<filter.length;i++) {
			for (int j = 0; j<filter[0].length;j++) {
				filter[i][j] = (int) (Math.random()*4-2); //NUMBERS ARE TOO BIG, GIVE NO FLEXIBILITY
			}
		}
		return filter;
	}
	
	public static int[] twoDtoOne(FeatureMap a) {
		int[] neuralNetworkInput = new int[a.processedPhoto.length*a.processedPhoto[0].length];
		for (int i = 0;i<a.processedPhoto.length;i++) {
			for (int j = 0;j<a.processedPhoto[0].length;j++) {
				int directory = j+i*a.processedPhoto[0].length;
				neuralNetworkInput[directory] = a.processedPhoto[i][j];
			}
		}
		return neuralNetworkInput;
	}
	
	public static double[] connectArrays(int[] a, int[] b) {
		double[] newArray = new double[a.length+b.length];
		for (int i = 0;i<a.length;i++) {
			newArray[i] = a[i];
		}
		for (int j = 0;j<b.length;j++) {
			newArray[j+a.length] = b[j];
		}
		return newArray;
	}
	
	public static double[] connectDoubleArrays(double[] a, double[] b) {
		double[] newArray = new double[a.length+b.length];
		for (int i = 0;i<a.length;i++) {
			newArray[i] = a[i];
		}
		for (int j = 0;j<b.length;j++) {
			newArray[j+a.length] = b[j];
		}
		return newArray;
	}
	
	public static String[] connectInputs(String[] a, String[] b) {
		String[] newArray = new String[a.length+b.length];
		for (int i = 0;i<a.length;i++) {
			newArray[i] = a[i];
		}
		for (int j = 0;j<b.length;j++) {
			newArray[j+a.length] = b[j];
		}
		return newArray;
	}
}
