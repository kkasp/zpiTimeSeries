package com.zpi.timeseries.neuralnetwork.transferprocessors;

public class SignumTransferProcessor implements TransferProcessor
{
	public SignumTransferProcessor()
	{}
	
	@Override
	public double process(double sourceValue)
	{
		return Math.signum(sourceValue);
	}
}
