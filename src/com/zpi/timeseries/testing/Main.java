package com.zpi.timeseries.testing;

import java.util.Arrays;

import com.zpi.timeseries.neuralnetwork.NeuralNetwork;
import com.zpi.timeseries.neuralnetwork.transferprocessors.SigmoidTransferProcessor;

public class Main
{
	public static void main(String[] args)
	{
		NeuralNetwork network = new NeuralNetwork(new int[] {2, 3, 2},
				new SigmoidTransferProcessor());
		
		System.out.println(Arrays.toString(network.processInput(new double[] {
				1, 0})));
	}
}
