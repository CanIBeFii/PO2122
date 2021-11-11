package ggc.core;

public abstract class Sale extends Transaction{

	protected Sale(Product p,int quantity, double price, Partner part, int id, Date date){
		super(p, quantity, price, part, id, date);
	}

	/*public String toString(){
		return String.format("VENDA|%s|%s|%s|%d|%d|%d|%d", getId(),
			getPartner().getId(), getProduct(), getQuantity(),
				getBaseValue(), x, y, getPaymentDate());
	}*/
}
	
