package ggc.core;

public class SaleByCredit extends Sale{

	private Date _deadline;
	private double _amountPaid;

	protected SaleByCredit(Product p,int quantity, double price, Partner part,Date deadline, int id, Date date){

		super(p, quantity, price, part, id, date);
		_deadline = deadline;
		_amountPaid = getBaseValue();
	}

	
	/** 
	 * @return Date
	 */
	public Date getDeadline(){
		return _deadline;
	}

	
	/** 
	 * @return double
	 */
	public double getAmountPaid(){
		return _amountPaid;
	}

	
	/** 
	 * @param d
	 * @param price
	 */
	public void setPaymentDate(Date d, double price){
		if(_paymentDate.getDays() == 0){
			_paymentDate.add(d.getDays());
			setAmountPaid(price);
		}
	}

	
	/** 
	 * @param price
	 */
	public void setAmountPaid(double price){
		int N = getProduct().getN();
		_amountPaid = applyPenalty(getBaseValue(), getP(N));
		
	}

	
	/** 
	 * @param N
	 * @return int
	 */
	public int getP(int N){
		if ((getDeadline().getDays() - getPaymentDate().getDays()) >= N){
			return 1;
		}
		else if (0 <= (getDeadline().getDays() - getPaymentDate().getDays()) && 
				(getDeadline().getDays() - getPaymentDate().getDays()) > N){
			return 2;
		}
		else if (0 < (getPaymentDate().getDays() - getDeadline().getDays()) &&
				(getDeadline().getDays() - getPaymentDate().getDays()) <= N){
			return 3;
		}
		else {
			return 4;
		}
	}

	
	/** 
	 * @param basePrice
	 * @param p
	 * @return double
	 */
	public double applyPenalty(double basePrice, int p){
		if (p == 1){
			return basePrice * 0.9;
		}
		else if(p == 2){
			if (_partner.getStatus().equals("Normal")){
				return basePrice;
			}
			else if (_partner.getStatus().equals("Selection")){
				if(getPaymentDate().getDays() - getDeadline().getDays() > 2){
					return basePrice;
				}
				return basePrice * 0.95;
			}
			else {
				return basePrice * 0.9;
			}
		}
		else if(p == 3){
			if (_partner.getStatus().equals("Normal")){
				_partner.changeStatus(getPaymentDate().getDays() - getDeadline().getDays());
				return basePrice * (getPaymentDate().getDays() - getDeadline().getDays() * 0.05 + 1);
			}
			else if (_partner.getStatus().equals("Selection")){
				if(getPaymentDate().getDays() - getDeadline().getDays() > 1){
					_partner.changeStatus(getPaymentDate().getDays() - getDeadline().getDays());
					return basePrice * (getPaymentDate().getDays() - getDeadline().getDays() * 0.02 + 1);
				}
				_partner.changeStatus(getPaymentDate().getDays() - getDeadline().getDays());
				return basePrice;
			}
			else {
				_partner.changeStatus(getPaymentDate().getDays() - getDeadline().getDays());
				return basePrice * 0.95;
			}
		}
		else{
			if (_partner.getStatus().equals("Normal")){
				_partner.changeStatus(getPaymentDate().getDays() - getDeadline().getDays());
				return basePrice * (getPaymentDate().getDays() - getDeadline().getDays() * 0.1 + 1);
			}
			else if (_partner.getStatus().equals("Selection")){
				_partner.changeStatus(getPaymentDate().getDays() - getDeadline().getDays());	
				return basePrice * (getPaymentDate().getDays() - getDeadline().getDays() * 0.05 + 1);
			}
			else {
				_partner.changeStatus(getPaymentDate().getDays() - getDeadline().getDays());
				return basePrice;
			}
		}
	}

	
	/** 
	 * @return String
	 */
	public String toString(){
		return String.format("VENDA|%s|%s|%s|%d|%d|%d|%d", getId(),
			getPartner().getId(), getProduct().getId(), getQuantity(),
				(int)Math.round(getBaseValue()), (int)Math.round(_amountPaid), _deadline.getDays(), getPaymentDate().getDays());
	}

	
	/** 
	 * @return String
	 */
	public String getType(){
		return "SaleByCredit";
	}
}