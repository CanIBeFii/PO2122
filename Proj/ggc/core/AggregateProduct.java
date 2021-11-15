package ggc.core;

import java.io.Serializable;

public class AggregateProduct extends Product{
	private Recipe _recipe;

	protected AggregateProduct(String id, Recipe recipe){
		super(id);
		_recipe = recipe;
	}

	protected void checkQuantity(int quantity, Partner p){
	}

	public String toString(){
		return String.format("%s|%d|%d|%s", getId(), (int)Math.round(getPrice()), getQuantity(), _recipe.toString());
	}

	public String getType(){
		return "AggregateProduct";
	}

	public Recipe getRecipe(){
		return _recipe;
	}
	
	public int getN(){
		return 3;
	}
}
