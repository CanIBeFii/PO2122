package ggc.core;

public class BreakdownSale extends Sale{

	private Date _deadline;
	private double _amountPaid;
	public Batch _batch;  

	protected BreakdownSale(Product p,int quantity, double price, Partner part, int id, Date date){

		super(p, quantity, price, part, id, date);
		_deadline = date;
		_amountPaid = getBaseValue();
	}

	public Batch getBatchNumber(){
		return _batch;
	}

	@Override
	public String toString(){
		return String.format("DESAGREGAÇÃO|%d|%s|%s|%d|%d|%d|%d|%s", getId(), 
			getPartner().getId(), getProduct().getId(), getQuantity(), 
				(int)Math.round(getBaseValue()), (int)Math.round(_amountPaid), _deadline.getDays(), getPaymentDate().getDays(), getProduct().getRecipe().toString());
	}


	public String getType(){
		return "BreakdownSale";
	}

	public double getAmountPaid(){
		return getBaseValue();
	}
}