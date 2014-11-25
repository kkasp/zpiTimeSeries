package com.zpi.timeseries.testing;

import java.util.Arrays;

import com.zpi.timeseries.neuralnetwork.NeuralNetwork;
import com.zpi.timeseries.neuralnetwork.learning.HebbLearner;
import com.zpi.timeseries.neuralnetwork.transferprocessors.SigmoidTransferProcessor;

public class Main
{
	public static void main(String[] args)
	{
		NeuralNetwork network = new NeuralNetwork(new int[] {2, 3, 2}, new SigmoidTransferProcessor(), new HebbLearner(.2));
		
		System.out.println(Arrays.toString(network.processInput(new double[] {
				3, 23})));
		System.out.println(Arrays.toString(network.processInput(new double[] {
				54, 3})));
		System.out.println(Arrays.toString(network.processInput(new double[] {
				22, 21})));
		System.out.println(Arrays.toString(network.processInput(new double[] {
				0, 54})));
		System.out.println(Arrays.toString(network.processInput(new double[] {
				21, 53})));
	}
}
