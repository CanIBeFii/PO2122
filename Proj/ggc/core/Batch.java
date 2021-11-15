package ggc.core;

import java.io.Serializable;

public class Batch implements Serializable{
	private Partner _partner;
	private Product _product;
	private double _price;
	private int _quantity;
	private int _id;

	public Batch(Partner partner, Product product, int quantity, double price, int id){
		_partner = partner;
		_price = price;
		_product = product;
		_quantity = quantity;
		_id = id;
	}
	
	/**
   	* @return  the quantity of product in the batch
   	*/
	public int getQuantity(){
		return _quantity;
	}
	
	/**
   	* @return  the id of the batch
   	*/
	public int getId(){
		return _id;
	}
	
	/**
   	* @param q
   	* @return  
   	*/
	public void removeQuantity(int q){
		if(q <= _quantity){
			_quantity -= q;
		}
	}
	
	/**
   	* @return the price of the batch
   	*/
	public double getPrice(){
		return _price;
	}

	/**
   	* @return the partner associated with the batch
   	*/
	public Partner getPartner(){
		return _partner;
	}
	
	/**
   	* @return the product of the batch
   	*/
	public Product getProduct(){
		return _product;
	}
	
	/**
   	* @return the format of each batch, first the product, secondly the partner, third the price rounded up, and lastly the quantity
   	*/
	public String toString(){
		return String.format("%s|%s|%d|%d", _product.getId(), _partner.getId(), (int)Math.round(_price), _quantity);
	}

}
