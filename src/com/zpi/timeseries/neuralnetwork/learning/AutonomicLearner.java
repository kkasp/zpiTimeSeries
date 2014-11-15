package com.zpi.timeseries.neuralnetwork.learning;

import java.util.List;

import com.zpi.timeseries.neuralnetwork.NeuronInput;

public abstract class AutonomicLearner
{
	public abstract void correctWeights(List<NeuronInput> inputs, double output);
}
