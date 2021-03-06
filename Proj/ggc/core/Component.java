package ggc.core;

import java.io.Serializable;

public class Component implements Serializable{
    private int _quantity;
    private Product _component;

    public Component(int quantity, Product component){
        _quantity = quantity;
        _component = component;
    }
    public int getQuantity(){
        return _quantity;
    }

    public Product getComponent(){
        return _component;
    }

    public String toString(){
        return "" + _component.getId() + ":" + _quantity + "#";
    }

    public String toStringBreakdown(){
        return "" + _component.getId() + ":" + _quantity;
    }
}
