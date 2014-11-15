package com.zpi.timeseries.neuralnetwork;

import java.util.ArrayList;
import java.util.List;

import com.zpi.timeseries.neuralnetwork.learning.AutonomicLearner;
import com.zpi.timeseries.neuralnetwork.transferprocessors.TransferProcessor;

public class NeuralNetwork
{
	private NeuralLayer[] layers;
	private TransferProcessor processor;
	private AutonomicLearner autonomicLearner;
	
	public NeuralNetwork(int[] neuronCounts, TransferProcessor processor)
	{
		this(neuronCounts, processor, null);
	}
	
	public NeuralNetwork(int[] neuronCounts, TransferProcessor processor,
			AutonomicLearner autonomicLearner)
	{
		layers = new NeuralLayer[neuronCounts.length];
		
		for(int i = 0; i < neuronCounts.length; i++)
		{
			// Tworzenie neuronów i warstwy
			List<Neuron> neurons = new ArrayList<>();
			for(int j = 0; j < neuronCounts[i]; j++)
			{
				// Neurony warstwy wejściowej mają po jednym wejściu
				neurons.add(new Neuron((i == 0 ? 1 : layers[i - 1].getNeurons()
						.size()), processor));
			}
			
			layers[i] = new NeuralLayer(neurons);
			
			// Łączenie z poprzednią warstwą
			if(i > 0)
				connectLayers(layers[i - 1], layers[i]);
		}
		
		// Ustawienie procesora
		this.setProcessor(processor);
		this.setAutonomicLearner(autonomicLearner);
	}
	
	public double[] processInput(double[] input)
	{
		if(input.length != layers[0].getNeurons().size())
			throw new IllegalArgumentException(
					"Input length must be equal to input layer neuron count!");
		
		// Ustawianie wejść
		for(int i = 0; i < input.length; i++)
		{
			layers[0].getNeurons().get(i).getInputs().get(0)
					.setInputValue(input[i]);
		}
		
		// Processing
		for(NeuralLayer layer : layers)
		{
			for(Neuron neuron : layer.getNeurons())
			{
				neuron.process();
			}
		}
		
		// Tworzenie wektora wyjściowego
		List<Neuron> outputNeurons = layers[layers.length - 1].getNeurons();
		double[] output = new double[outputNeurons.size()];
		
		for(int i = 0; i < outputNeurons.size(); i++)
		{
			output[i] = outputNeurons.get(i).getValue();
		}
		
		return output;
	}
	
	void connectLayers(NeuralLayer previous, NeuralLayer next)
	{
		for(Neuron neuron : next.getNeurons())
		{
			List<NeuronInput> inputs = neuron.getInputs();
			
			for(int i = 0; i < inputs.size(); i++)
			{
				neuron.getInputs().get(i)
						.setSource(previous.getNeurons().get(i));
			}
		}
	}
	
	public double getWeight(int layerIndex, int neuronIndex, int inputIndex)
	{
		NeuralLayer layer = layers[layerIndex];
		Neuron neuron = layer.getNeurons().get(neuronIndex);
		return neuron.getInputs().get(inputIndex).getWeight();
	}
	
	public void setWeight(int layerIndex, int neuronIndex, int inputIndex,
			double weight)
	{
		NeuralLayer layer = layers[layerIndex];
		Neuron neuron = layer.getNeurons().get(neuronIndex);
		neuron.getInputs().get(inputIndex).setWeight(weight);
	}
	
	public TransferProcessor getProcessor()
	{
		return processor;
	}
	
	public void setProcessor(TransferProcessor processor)
	{
		this.processor = processor;
		for(NeuralLayer layer : layers)
		{
			for(Neuron neuron : layer.getNeurons())
			{
				neuron.setProcessor(processor);
			}
		}
	}
	
	public AutonomicLearner getAutonomicLearner()
	{
		return autonomicLearner;
	}
	
	public void setAutonomicLearner(AutonomicLearner autonomicLearner)
	{
		this.autonomicLearner = autonomicLearner;
		for(NeuralLayer layer : layers)
		{
			for(Neuron neuron : layer.getNeurons())
			{
				neuron.setAutononicLearner(autonomicLearner);
			}
		}
	}
}
