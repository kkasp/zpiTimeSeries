package com.zpi.timeseries.neuralnetwork;

import java.util.List;

public class NeuralLayer
{
	private List<Neuron> neurons;
	
	NeuralLayer(List<Neuron> neurons)
	{
		this.neurons = neurons;
	}
	
	public List<Neuron> getNeurons()
	{
		return neurons;
	}
}
