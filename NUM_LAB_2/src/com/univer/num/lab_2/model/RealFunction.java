package com.univer.num.lab_2.model;

public class RealFunction {
	public static double valueInPointReal(double x){
		if ((x >= -3.0)&&(x < 0.0)) return 0;
		if ((x >= 0.0)&&(x < 3.0)) return x;
		return 0;
	}
}