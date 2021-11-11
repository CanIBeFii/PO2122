package ggc.core;

import java.io.Serializable;

// Needs to get information of Products somehow
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
}
