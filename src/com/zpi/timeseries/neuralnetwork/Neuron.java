package com.zpi.timeseries.neuralnetwork;

import java.util.ArrayList;
import java.util.List;

public class Neuron
{
	private double value;
	private List<NeuronInput> inputs;
	private TransferProcessor processor;
	
	public Neuron(int inputCount, TransferProcessor processor)
	{
		inputs = new ArrayList<NeuronInput>();
		
		for(int i = 0; i < inputCount; i++)
		{
			inputs.add(new NeuronInput());
		}
		
		this.processor = processor;
	}
	
	public List<NeuronInput> getInputs()
	{
		return inputs;
	}
	
	public double getValue()
	{
		return value;
	}
	
	public void process()
	{
		double initialValue = .0;
		
		for(NeuronInput input : inputs)
		{	
			initialValue += input.getValue() * input.getWeight();
		}
		
		value = processor.process(initialValue);
	}
}
