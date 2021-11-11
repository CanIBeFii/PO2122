package ggc.core;

import java.util.*;
import java.io.Serializable;
import java.math.*;

public class Partner implements Serializable{
	private String _name;
	private String _address;
	private String _id;
	private String _status;
	private double _points;
	private List<SaleByCredit> _sales;
	private List<BreakdownSale> _breakdownSales;
	private List<Acquisition> _acquisitions;
	private Set<Batch> _batches;
	private Set<Notification> _notifications;


	public Partner(String name, String address, String id){
		_name = name;
		_address = address;
		_id = id;
		_status = "NORMAL";
		List<SaleByCredit> s = new ArrayList<>();
		_sales = s;
		List<BreakdownSale> bs = new ArrayList<>();
		_breakdownSales = bs;
		List<Acquisition> a = new ArrayList<>();
		_acquisitions = a;

	}

	/**
   	* @return  the name of the partner
   	*/
	public String getName(){
		return _name;
	}
	
	/**
   	* @return the format of each partner, in the following order, id, name, adress, status and number of points rounded
   	*/
	public String toString(){
		return String.format("%s|%s|%s|%s|%d|%d|%d|%d", _id, _name, _address, _status, (int)Math.round(_points), 
			(int)Math.round(getValueAcquisitions()), (int)Math.round(getValueSales()), (int)Math.round(getValueSalesPaid()));
	}
	
	/**
   	* @return the Id of the partner
   	*/
	public String getId(){
		return _id;
	}

	/**
   	* @return the adress of the partner
   	*/
	public String getAddress(){
		return _address;
	}
	
	/**
   	* @return the status of the partner
   	*/
	public String getStatus(){
		return _status;
	}
	
	/**
   	* @return the number of points belonging to the partner
   	*/
	public double getPoints(){
		return _points;
	}

	/**
	 * @return the list of acquisitions of the partner
	 */
	public List<Acquisition> getAcquisitions(){
		return _acquisitions;
	}
	
	/**
	 * @return the list of sales of the partner
	 */
	public List<Sale> getSales(){
		List<Sale> sales = new ArrayList<>();
		sales.addAll(_sales);
		sales.addAll(_breakdownSales);
		return sales;
	}
	
	/**
   	* @return the new number of points
   	*/
	public void changePoints(double morePoints){/*Ver se a funcoes que dão isto verificam se estamos a tirar todos so pontos deles ou se tem de se por uma Exception*/
		_points += morePoints;
	}
	
	public void changeStatus(){
		if (_points > 25000){
			_status = "Elite";
		}
		else if (_points > 2000 ){
			_status = "Selection";
		}
	}

	public void changeStatus(int days){
		if(_status.equals("Normal")){
			_points = 0;
		}
		else if(_status.equals("Selection") && days > 2){
			_status = "Normal";
			_points *= 0.1; 
		}
		else if (_status.equals("Elite") && days > 15){
			_status = "Selection";
			_points *= 0.25;
		}
	}

	public double getValueAcquisitions(){
		double price = 0;
		if(_acquisitions == null){
			return price;
		}
		for (Acquisition a: _acquisitions){
			price += a.getBaseValue() * a.getQuantity();
		}
		return price;
	}

	public double getValueSales(){
		double res = 0;
		if(_sales == null){
			return res;
		}
		for(SaleByCredit s : _sales){
			res += s.getBaseValue();
		}
		return res;
	}

	public double getValueSalesPaid(){
		double res = 0;
		if (_sales == null){
			return res;
		}
		for(SaleByCredit s : _sales){
			res += s.getAmountPaid();
		}
		return res;
	}

	/**
	*Adds a sale to the partner
   	* @param s
   	*/
	public void addSale(SaleByCredit s){
		_sales.add(s);
	}

	public void addBreakdown(BreakdownSale bs){
		_breakdownSales.add(bs);
	}
	
	/**
	*Adds a Acquisition to the partner
   	* @param a
   	*/
	public void addAcquisition(Acquisition a){
		_acquisitions.add(a);
	}
	
	/**
	*Adds a batch to the partner
   	* @param b
   	*/
	public void addBatch(Batch b){
		_batches.add(b);
	}

	/**
	*Adds a notification to the partner
   	* @param n
   	*/
	public void addNotification(Notification n){
		_notifications.add(n);
	}

	/**
	*Removes a batch from the partner
   	* @param b
   	*/
	public void removeBatch(Batch b){
		Iterator<Batch> iter = _batches.iterator();
		
		while(iter.hasNext()){
			Batch batch = iter.next();
			if(batch.equals(b)){
				iter.remove();
				break;
			}
		}
	}

	/**
	*Removes a notification from the partner
   	* @param n
   	*/
	public void removeNotification(Notification n){
		Iterator<Notification> iter = _notifications.iterator();
		
		while(iter.hasNext()){
			Notification notif = iter.next();
			if(notif.equals(n)){
				iter.remove();
				break;
			}
		}
	}
}