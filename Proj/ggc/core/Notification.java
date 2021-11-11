package ggc.core;

import java.io.Serializable;

public class Notification implements Serializable{

	private String _type;
	private Product _product;

	public Notification(String type, Product product){
		_type = type;
		_product = product;
	}

	public String getType(){
		return _type;
	}

	public Product getProduct(){
		return _product;
	}

	@Override
	public boolean equals(Object obj){
		return obj instanceof Notification && _type.equals(((Notification)obj).getType()) && _product.getId().equals(((Notification)obj).getProduct().getId()); 
	}

	public String toString(){
		return "";
	}
}

