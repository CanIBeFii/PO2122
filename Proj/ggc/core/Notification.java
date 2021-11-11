package ggc.core;

import java.io.Serializable;

public class Notification implements Serializable{

	private String _type;
	private Product _product;
	private boolean _notify;
	private String _description;

	public Notification(String type, Product product){
		_type = type;
		_product = product;
		_notify = false;
	}

	public String getType(){
		return _type;
	}

	public Product getProduct(){
		return _product;
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
		return _type + "|" + _product.getId() + "|"+ _product.getPrice();
	}
}
