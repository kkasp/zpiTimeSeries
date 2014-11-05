package com.zpi.timeseries.neuralnetwork;

import java.util.concurrent.Callable;

public interface TransferProcessor
{
	public abstract double process(double sourceValue);
}
