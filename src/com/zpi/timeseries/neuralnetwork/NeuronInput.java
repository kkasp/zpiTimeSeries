package com.zpi.timeseries.neuralnetwork;

public class NeuronInput
{
	private double weight;
	private double inputValue;  // Używane tylko, gdy nie ma innego neuronu na wejściu
	private Neuron source;

	public NeuronInput()
	{
		inputValue = 0.0;
		weight = 1.0;
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
	
	double getValue()
	{
		if(source != null)
			return source.getValue();
		else
			return inputValue;
	}

	Neuron getSource()
	{
		return source;
	}

	public void setSource(Neuron source)
	{
		this.source = source;
	}
}
