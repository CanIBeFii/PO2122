package ggc.core;

import java.math.*;

public class BreakdownSale extends Sale{

	private Date _deadline;
	private double _amountPaid;
	public Batch _batch;  

	protected BreakdownSale(Product p,int quantity, double price, Partner part, int id, Date date){

		super(p, quantity, price, part, id, date);
		_deadline = date;
		if(price < 0){
			_amountPaid = 0;
		}
		else{
			_amountPaid = getBaseValue();
		}
	}

	public Batch getBatchNumber(){
		return _batch;
	} 

	public String productRecipeString(){
		StringBuilder res = new StringBuilder();
		for(Component c: getProduct().getRecipe().getComponents()){
			res.append(c.getComponent().getId() + ":" + (c.getQuantity() * getQuantity()) +":" + (int)Math.round(c.getComponent().getPrice()* getQuantity() * c.getQuantity()) + "#");
		}
		res.deleteCharAt(res.length() - 1);
		String s = res.toString();
		return s;
	}

	@Override
	public String toString(){
		return String.format("DESAGREGAÇÃO|%d|%s|%s|%d|%d|%d|%d|%s", getId(), 
			getPartner().getId(), getProduct().getId(), getQuantity(), 
				(int)Math.round(getBaseValue()), (int)Math.round(_amountPaid), _deadline.getDays(), productRecipeString());
	}


	public String getType(){
		return "BreakdownSale";
	}

	public double getAmountPaid(){
		return _amountPaid;
	}

	public void setAmountPaid(){
		return;
	}
}