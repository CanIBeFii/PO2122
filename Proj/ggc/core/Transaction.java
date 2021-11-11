package ggc.core;

import java.io.Serializable;

public abstract class Transaction implements Serializable{

	private int _id;
	protected Date _paymentDate;
	protected double _baseValue;
	private double _unitPrice;
	private double _valuePaid;
	private int _quantity;
	private Product _productName;
	protected Partner _partner;

	public Transaction(Product p,int quantity, double price, Partner part, int id, Date date){
		_id = id;
		_productName = p;
		_quantity = quantity;
		_partner = part;
		_unitPrice = price / quantity;
		_baseValue = price;
		_paymentDate = date;
	}
    
	public int getId(){
		return _id;
	}

	public Date getPaymentDate(){
		return _paymentDate;
	}

	public void setPaymentDate(Date d){
		if(_paymentDate.getDays() == 0){
			_paymentDate.add(d.getDays());
		}
	}

	public double getBaseValue(){
		return _baseValue;
	}

	public double getUnitPrice(){
		return _unitPrice;
	}

	public int getQuantity(){
		return _quantity;
	}

	public Product getProduct(){
		return _productName;
	}

	public Partner getPartner(){
		return _partner;
	}

	public boolean isPaid(){
		if(_paymentDate.getDays() == 0){
			return false;
		}
		return true;
	}

	public abstract String getType();

	public abstract double getAmountPaid();
}