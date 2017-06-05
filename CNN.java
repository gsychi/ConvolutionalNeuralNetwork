package PROCESSING;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class CNN {

	public static void main(String[] args) throws IOException {

		//GRABBING PHOTOS
		String[] humans = {"sampleTzuyuIcon.png", "humanSampleIcon.jpeg","stevejobsSampleIcon.jpg","barackobamaSampleIcon.jpg","kevinloveSampleIcon.png","sanaSampleIcon.jpg","jihyoSampleIcon.jpg","macronSampleIcon.jpg"};
		String[] vegetables = {"potato1.png","potato2.png","potato3.png","potato4.jpg","potato5.png","potato6.png","potato7.png","potato8.png","potato9.jpg"};
		String[] photoNames = mxjava.connectInputs(humans, vegetables);
		BufferedImage[] photoBank = new BufferedImage[photoNames.length];
		double[] results = new double[photoNames.length];
		for (int i = 0;i<photoNames.length;i++) {
			photoBank[i]=mxjava.retrievePhoto(photoNames[i]);
			if(i<humans.length) results[i] = 1;
			else results[i] = 0;
		}

		String[] photoLink = {"unseen.jpg"};
		BufferedImage[] unseenPhotos = new BufferedImage[photoLink.length];
		for (int i = 0;i<unseenPhotos.length;i++) {
			unseenPhotos[i] = mxjava.retrievePhoto(photoLink[i]);
		}

		double[][] trainingDataInput = new double[photoBank.length][ConvolutionConstruction.processed(photoBank[0]).length];
		double[][] trainingDataOutput = new double[photoBank.length][1];
		for (int i = 0;i<trainingDataInput.length;i++) {
			trainingDataInput[i]=ConvolutionConstruction.processed(photoBank[i]);
			double[] addTrainingOutput = {results[i]};
			trainingDataOutput[i]=addTrainingOutput;
		}

		int NODES_PER_LAYER = 15;
		double LEARNING_RATE = 0.05;
		double RUNS = 80;
		double wrong = 0;
		
		for (int i = 0;i<RUNS;i++) {
			ANN neuralNetwork = new ANN(trainingDataInput,trainingDataOutput,NODES_PER_LAYER,LEARNING_RATE);
			neuralNetwork.trainNetwork(20000);

			//So! Is Tzuyu A PERSON??
			for (int x = 0;x<unseenPhotos.length;x++) {
				double[][] humanProbability = new double[unseenPhotos.length][1];
				humanProbability[x] = neuralNetwork.predict(ConvolutionConstruction.processed(unseenPhotos[x]));
				System.out.println("쯔위 사진 (진짜 예뻐)// 周子瑜的相片 // TZUYU PHOTO PROCESSED RESULTS: " + Arrays.toString(neuralNetwork.predict(ConvolutionConstruction.processed(unseenPhotos[x]))));
				if (humanProbability[x][0]<0.5) {
					System.out.println("쯔위는 감자 이야 // 周子瑜是個馬鈴薯 // Tzuyu is a potato :(");
					wrong++;
					}
				else System.out.println("쯔위는 사람 이야 //  周子瑜是個人 // Tzuyu is a person!");
			}
		}
		System.out.println(((RUNS-wrong)/RUNS)*100 + "% correct.");
		//neuralNetwork.printSynapses();
	}
}
