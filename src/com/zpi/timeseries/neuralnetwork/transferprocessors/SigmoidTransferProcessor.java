package com.zpi.timeseries.neuralnetwork.transferprocessors;

public class SigmoidTransferProcessor implements TransferProcessor
{
	public SigmoidTransferProcessor()
	{}
	
	@Override
	public double process(double sourceValue)
	{
		return 2.0 / (1.0 + Math.exp(-sourceValue)) - 1.0;
	}
}
