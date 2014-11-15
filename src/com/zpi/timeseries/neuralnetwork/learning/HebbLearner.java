package com.zpi.timeseries.neuralnetwork.learning;

import java.util.List;

import com.zpi.timeseries.neuralnetwork.NeuronInput;

public class HebbLearner extends AutonomicLearner
{
	private double learnFactor;
	
	public HebbLearner(double learnFactor)
	{
		this.setLearnFactor(learnFactor);
	}
	
	@Override
	public void correctWeights(List<NeuronInput> inputs, double output)
	{
		for(NeuronInput input : inputs)
		{
			double weight = input.getWeight();
			input.setWeight(weight + learnFactor * input.getValue() * output);
			// System.out.println("Weight changed: " + weight + " -> " + input.getWeight());  // Debug
		}
	}
	
	public double getLearnFactor()
	{
		return learnFactor;
	}
	
	public void setLearnFactor(double learnFactor)
	{
		this.learnFactor = learnFactor;
	}
	
}
