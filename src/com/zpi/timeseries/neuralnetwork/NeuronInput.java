package com.zpi.timeseries.neuralnetwork;

import java.util.Random;

public class NeuronInput
{
	private double weight;
	private double inputValue;  // Używane tylko, gdy nie ma innego neuronu na wejściu
	private Neuron source;

	public NeuronInput()
	{
		inputValue = 0.0;
		weight = new Random().nextDouble()*0.000001; // W ten sposób mamy liczbę z zakresu 0-0.2
		source = null;
	}
	
	public double getWeight()
	{
		return weight;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	public void setInputValue(double value)
	{
		inputValue = value;
	}
	
	public double getValue()
	{
		if(source != null)
			return source.getValue();
		else
			return inputValue;
	}

	public Neuron getSource()
	{
		return source;
	}

	public void setSource(Neuron source)
	{
		this.source = source;
	}
}
