package ggc.core;

import java.io.Serializable;

public abstract class Transaction implements Serializable{

	private int _id;
	protected Date _paymentDate;
	protected double _baseValue;
	private double _unitPrice;
	private int _quantity;
	private Product _productName;
	protected Partner _partner;
	private boolean _paid;

	public Transaction(Product p,int quantity, double price, Partner part, int id, Date date){
		_id = id;
		_productName = p;
		_quantity = quantity;
		_partner = part;
		_unitPrice = price / quantity;
		_baseValue = price;
		_paymentDate = date;
		_paid = false;
	}
    
	 
	/** 
	 * @return int
	 */
	public int getId(){
		return _id;
	}

	
	/** 
	 * @return Date
	 */
	public Date getPaymentDate(){
		return _paymentDate;
	}

	
	/** 
	 * @param d
	 */
	public void setPaymentDate(Date d){
		_paymentDate.add(d.getDays() - _paymentDate.getDays());
		setAmountPaid();
	}

	
	/** 
	 * @return double
	 */
	public double getBaseValue(){
		return _baseValue;
	}

	
	/** 
	 * @return double
	 */
	public double getUnitPrice(){
		return _unitPrice;
	}

	
	/** 
	 * @return int
	 */
	public int getQuantity(){
		return _quantity;
	}

	
	/** 
	 * @return Product
	 */
	public Product getProduct(){
		return _productName;
	}

	
	/** 
	 * @return Partner
	 */
	public Partner getPartner(){
		return _partner;
	}

	public boolean setPaid(){
		if(!_paid){
			_paid = true;
			_partner.changePoints(getAmountPaid() * 10);
		}
		return _paid;
	}
	/** 
	 * @return boolean
	 */
	public boolean isPaid(){
		return _paid;
	}

	public abstract String getType();

	public abstract double getAmountPaid();

	public abstract void setAmountPaid();
}