package com.zpi.timeseries.testing;

import java.util.Arrays;

import com.zpi.timeseries.neuralnetwork.LinearTransferProcessor;
import com.zpi.timeseries.neuralnetwork.NeuralNetwork;

public class Main
{
	public static void main(String[] args)
	{
		NeuralNetwork network = new NeuralNetwork(new int[] {2, 3, 2},
				new LinearTransferProcessor(0.3));
		
		// Tak, każdą wagę trzeba wpisać osobno. Oczywiście będzie wczytywane z
		// pliku.
		network.setWeight(1, 0, 0, 0.2);
		network.setWeight(1, 0, 1, 0.35);
		network.setWeight(1, 1, 0, 0.6);
		network.setWeight(1, 1, 1, 0.2);
		network.setWeight(1, 2, 0, 0.03);
		network.setWeight(1, 2, 1, 0.35);
		
		network.setWeight(2, 0, 0, -2.1);
		network.setWeight(2, 0, 1, 0.34);
		network.setWeight(2, 0, 2, 0.83);
		network.setWeight(2, 0, 0, 0.21);
		network.setWeight(2, 1, 1, -0.34);
		network.setWeight(2, 1, 2, 0.5);
		
		System.out.println(Arrays.toString(network.processInput(new double[] {
				1, 0})));
	}
}
