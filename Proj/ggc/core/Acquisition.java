package ggc.core;

public class Acquisition extends Transaction{

	protected Acquisition(Product p,int quantity, double price, Partner part, int id, Date date){
		super(p, quantity, price, part, id, date);
	}

	
	
	/** 
	 * @return String
	 */
	public String toString(){
		return String.format("COMPRA|%s|%s|%s|%d|%d|%d", getId(), 
			getPartner().getId(), getProduct().getId(), getQuantity(),((int)Math.round(getBaseValue()) * getQuantity()), getPaymentDate().getDays());
	}

	
	/** 
	 * @return String
	 */
	public String getType(){
		return "Acquisition";
	}

	
	/** 
	 * @return double
	 */
	public double getAmountPaid(){
		return getBaseValue() * getQuantity();
	}
}