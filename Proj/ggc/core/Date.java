package ggc.core;

import java.io.Serializable;

public class Date implements Serializable{

	private int _days;
	
	public Date(int n){
		_days = n;
	}
	
	public int getDays(){
		return _days;
	} 


	public void add(int days){
		if(days >= 0){
			_days += days;
		}
	}

	public int difference(Date other){
		return _days - other.getDays();

	}
}
