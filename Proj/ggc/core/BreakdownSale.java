package ggc.core;

public class BreakdownSale extends Sale{

	public Batch _batch;  //tem de ser vector por serem varios batch??

	protected BreakdownSale(Product p,int quantity, double price, Partner part, int id, Date date){

		super(p, quantity, price, part, id, date);
	}

	public Batch getBatchNumber(){
		return _batch;
	}

	//fazer o to String

}