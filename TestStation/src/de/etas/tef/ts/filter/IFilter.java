package de.etas.tef.ts.filter;

public interface IFilter<T>
{
	T filt(T input);
}
