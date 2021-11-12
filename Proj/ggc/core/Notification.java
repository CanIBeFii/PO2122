package ggc.core;

import java.io.Serializable;

public class Notification implements Serializable{

	private NotificationType _type;
	private Product _product;
	private boolean _notify;
	private String _description;
	private double _price;

	public Notification(Product product, String description, double price){
		_type = NotificationType.MAIL;
		_product = product;
		_description = description;
		_price = price;
		_notify = false;
	}

	public NotificationType getType(){
		return _type;
	}

	public Product getProduct(){
		return _product;
	}

	public String getDescription(){
		return _description;
	}
	
	public boolean hasNotify(){
		return _notify;
	}

	public void toggleNotify(){
		_notify = !_notify;
	}

	@Override
	public boolean equals(Object obj){
		return obj instanceof Notification && _type.equals(((Notification)obj).getType()) && _product.getId().equals(((Notification)obj).getProduct().getId()); 
	}

	public String toString(){
		return getDescription() + "|" + _product.getId() + "|" + (int)_price;
	}
}
