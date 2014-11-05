package com.zpi.timeseries.neuralnetwork;

public class LinearTransferProcessor implements TransferProcessor
{
	private double factor;
	
	public LinearTransferProcessor(double factor)
	{
		this.factor = factor;
	}
	
	@Override
	public double process(double sourceValue)
	{
		return factor * sourceValue;
	}

	public double getFactor()
	{
		return factor;
	}

	public void setFactor(double factor)
	{
		this.factor = factor;
	}
}
