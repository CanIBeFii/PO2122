package ggc.core;

import java.util.*;
import java.io.Serializable;

import ggc.core.AggregateProduct;


public class Recipe implements Serializable{
	private double _alpha;
	private List<Component> _components;
	private String _name;

	public Recipe(double alpha, List<Component> components, String name){
		_alpha = alpha;
		_components = new ArrayList<Component>();
		_components = components;
		_name = name; 
	}

	public double getAlpha(){
		return _alpha;
	}

	public List<Component> getComponents(){
		return _components;
	}

	public String getName(){
		return _name;
	}

	public String toString(){
		int i = 0;
		StringBuilder res = new StringBuilder();
		for(Component c: _components){
			res.append(c.getComponent().getId() + ":" + c.getQuantity() + "#");
		}
		res.deleteCharAt(res.length() - 1);
		String s = res.toString();
		return s;
	}
}
