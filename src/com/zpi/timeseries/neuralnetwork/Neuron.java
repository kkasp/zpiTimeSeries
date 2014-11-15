package com.zpi.timeseries.neuralnetwork;

import java.util.ArrayList;
import java.util.List;

import com.zpi.timeseries.neuralnetwork.learning.AutonomicLearner;
import com.zpi.timeseries.neuralnetwork.transferprocessors.TransferProcessor;

public class Neuron
{
	private double value;
	private List<NeuronInput> inputs;
	private TransferProcessor processor;
	private AutonomicLearner autononicLearner;
	
	public Neuron(int inputCount, TransferProcessor processor)
	{
		inputs = new ArrayList<NeuronInput>();
		
		for(int i = 0; i < inputCount; i++)
		{
			inputs.add(new NeuronInput());
		}
		
		this.processor = processor;
		this.autononicLearner = null;
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
		
		// Korekcja wag, jeżeli ustawiono learnera
		if(autononicLearner != null)
		{
			autononicLearner.correctWeights(inputs, value);
		}
	}
	
	public TransferProcessor getProcessor()
	{
		return processor;
	}

	public void setProcessor(TransferProcessor processor)
	{
		this.processor = processor;
	}

	public AutonomicLearner getAutononicLearner()
	{
		return autononicLearner;
	}

	public void setAutononicLearner(AutonomicLearner autononicLearner)
	{
		this.autononicLearner = autononicLearner;
	}
}
