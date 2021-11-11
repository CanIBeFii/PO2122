
package ggc.core;

import java.util.*;
import java.io.Serializable;
import java.math.*;

public abstract class Product implements Serializable{
    private double _maxPrice;
    private String _id;
    private int _quantity;

    public Product(String id){
        _id = id;
        _maxPrice = 0;
    }
    
    /**
   * @return the format of each product, first the product Id, secondly the price and lastly the quantity
   */
    public String toString(){
        return String.format("%s|%d|%d", _id,(int)Math.round(_maxPrice), _quantity);
    }

    protected abstract void checkQuantity(int quantity, Partner p);

	/**
   * @param quantity
   */
    public void addQuantity(int quantity){
        if(quantity > 0){
            _quantity += quantity;
        }
    }
	
    public void removeQuantity(int quantity){
        if (quantity > 0){
            _quantity -= quantity;
        }
    }
	/**
   * @return the price of the product
   */
    public double getPrice(){
        return _maxPrice; 
    }

    public int getQuantity(){
        return _quantity;
    }
	/**
   * @return the Id of the product
   */
    public String getId(){
        return _id;
    }

	/**
   * @return the new price of the product
   */
    public void setMaxPrice(double newPrice){
		if (newPrice > _maxPrice){
			_maxPrice = newPrice;
		}
    }
    
    public abstract int getN();
}
