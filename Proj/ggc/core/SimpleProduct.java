
package ggc.core;



public class SimpleProduct extends Product {
	
	protected SimpleProduct(String id){
		super(id);
	}

	protected void checkQuantity(int quantity, Partner p){
		
	}

	public String getType(){
		return "SimpleProduct";
	}

	public Recipe getRecipe(){
		return null;
	}

	public int getN(){
		return 5;
	}

}
