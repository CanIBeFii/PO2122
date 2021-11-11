package ggc.core;


import java.io.Serializable;
import java.io.IOException;

import ggc.core.Acquisition;
import ggc.core.Transaction;
import ggc.core.exception.BadEntryException;

import java.util.*;

/**
 * Class Warehouse implements a warehouse.
 */
 
 
 /**
 * Warehouse implementation.
 *
 * @author Filipe Mendes - 98934
 * @author Tiago Dinis - 98970
 * @version Intermedia
 * @since 29/10/2021
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	private int _nextTransactionId;
	private int _batchId;
	private double _contabilisticBalance;
	private double _availableBalance;
	private List<Transaction> _allTransactions;
	private List<Partner> _allPartners;
	private List<Product> _allProducts;
	private List<Recipe> _allRecipes;
	private List<Batch> _allBatches;
	private Date _date;


	public Warehouse(){
		List<Transaction> t = new ArrayList<>();
		List<Partner> pa = new ArrayList<>();
		List<Product> pr = new ArrayList<>();
		List<Recipe> r = new ArrayList<>();
		List<Batch> b = new ArrayList<>();
		_date = new Date(0);
		_allBatches = b;
		_allTransactions = t;
		_allPartners = pa;
		_allProducts = pr;
		_allRecipes = r;
	}


	//Recipes
	/**
   * 
   * @param name
   * @return
   */
	protected Recipe getRecipe(String name){
		Iterator<Recipe> iter = _allRecipes.iterator();
		while (iter.hasNext()){
			Recipe r = iter.next();
			if(name.equals(r.getName())){
				return r;
			}
		}
		return null;
	}
	
	/**
   * 
   * @return List that contains all the recipes
   */
	protected List<Recipe> getAllRecipes(){
		return this._allRecipes;
	}
	
	/**
   * @param alpha
   * @param components
   * @param name
   * @return
   */
	protected boolean registerRecipe(double alpha, List<Component> components, String name){
		if(this.getRecipe(name) != null){
			return false;
		}
		Recipe r = new Recipe(alpha, components, name);
		return _allRecipes.add(r);
	}


	//Partners
	/**
	 * @param id
	 * @return
	 */
	protected Partner getPartner(String id){
		for(Partner p: _allPartners){
			if(id.toUpperCase().equals(p.getId().toUpperCase())){
				return p;
			}
		}
		return null;
	}	
	
	/**
   * 
   * @return List that contains all the partners
   */
	protected List<Partner> getAllPartners(){
		return this._allPartners;
	}

	/**
	* @param name
	* @param adress
   	* @param id
   	* @return
   	*/
	protected boolean registerPartner(String name, String address, String id){
		if(this.getPartner(id) != null){
			return false;
		}
		Partner p = new Partner(name, address, id);
		return _allPartners.add(p);
	}


	//Product
	/**
   	* @param id
   	* @return
   	*/
	protected Product getProduct(String id){
		for(Product p: _allProducts){
			if(id.equals(p.getId())){
				return p;
			}
		}
		return null;
	}
	
	/**
   * 
   * @return List that contains all the products
   */
	protected List<Product> getAllProducts(){
		return this._allProducts;
	}
	
	/**
   	* @param id
   	* @return
   	*/
	protected boolean registerProduct(String id){
		if(this.getProduct(id) != null){
			return false;
		}
		SimpleProduct p = new SimpleProduct(id);
		return _allProducts.add(p);
	}

	//AggregateProduct
	/**
   	* @param id
   	* @param recipe
   	* @return
   	*/
	protected boolean registerProduct(String id, Recipe recipe){
		if(this.getProduct(id) != null){
			return false;
		}
		AggregateProduct ap = new AggregateProduct(id, recipe);
		return _allProducts.add(ap);
	}

	//Transactions
	/**
   	* @param idquantity
   	* @return
   	*/
	protected Transaction getTransaction(int id){
		for(Transaction t : _allTransactions){
			if( id == t.getId())
				return t;
		}
		return null;
	}
	
	/**
   * 
   * @return List that contains all the transactions
   */
	protected List<Transaction> getAllTransactions(){
		return this._allTransactions;
	}

	/**
	 * @param p
	 * @return List that contains all the acquisitions of a partner
	 */
	protected List<Acquisition> getPartnerAcquisitions(Partner p){
		return p.getAcquisitions();
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	protected List<Sale> getPartnerSales(Partner p){
		return p.getSales();
	}

	/**
	 * @param product
	 * @param quantity
	 * @param price
	 * @param part
	 * @return
	 */
	protected boolean registerAcquisition(Product product, int quantity, double price, Partner part){
		Acquisition a = new Acquisition(product,quantity, price, part, _nextTransactionId, _date);
		Partner p = getPartner(part.getId());
		if(_allTransactions.add(a)){
			p.addAcquisition(a);
			registerBatch(part, product, quantity, price);
			_nextTransactionId++;
			return true;
		}
		return false;
	}
	
	/**
	 * @param product
	 * @param quantity
	 * @param price
	 * @param part
	 * @return
	 */
	protected boolean registerBreakdownSale(Product product, int quantity, double price, Partner part){
		BreakdownSale bs = new BreakdownSale(product, quantity, price, part, _nextTransactionId, _date);
		if(_allTransactions.add(bs)){
			_nextTransactionId++;
			return true;
		}
		return false;
	}
	
	/**
	 * @param product
	 * @param quantity
	 * @param price
	 * @param part
	 * @param deadline
	 * @return
	 */
	protected boolean registerSaleByCredit(Product product, int quantity, Partner part, int deadline){
		Date deadDate = new Date(deadline);
		List<Batch> batches = getBatchProduct(product);

		Comparator<Batch> comparator = new Comparator<Batch>() {
			public int compare(Batch a, Batch b) {
				  return -((int)Math.round(a.getPrice()) - (int)Math.round(b.getPrice()));
			}
		};

		batches.sort(comparator);
		int amount = 0;
		double price = 0;

		for(Batch b: batches){
			if(amount < quantity){
				int i = b.getQuantity();
				while(i > 0 && amount < quantity){
					amount++;
					i--;
					removeQuantityBatch(b.getId(), 1);
					getProduct(product.getId()).removeQuantity(1);
					price += b.getPrice();
				}
			}
		}
		if (quantity != amount){
			return false;
		}
		SaleByCredit sc = new SaleByCredit(product, quantity, price, part, deadDate, _nextTransactionId, new Date(0));
		if(_allTransactions.add(sc)){
			_nextTransactionId++;
			return true;
		}
		return false;
	}
	

	//Batches
	/**
   	* @param id
   	* @return
   	*/
	protected Batch getBatch(int id){
		Iterator<Batch> iter = _allBatches.iterator();
		while(iter.hasNext()){
			Batch b = iter.next();
			if(id == b.getId()){
				return b;
			}
		}
		return null;
	}

	/**
   	* @param p
   	* @return
   	*/ 
	protected List<Batch> getBatchPartner(Partner p){
		List<Batch> res = new ArrayList<>();
		for(Batch b: _allBatches){
			if(b.getPartner().getId().equals(p.getId())){
				res.add(b);
			}
		}
		return res;
	}
	
	/**
   	* @param p
   	* @return
   	*/
	protected List<Batch> getBatchProduct(Product p){
		List<Batch> res = new ArrayList<>();
		for(Batch b: _allBatches){
			if(b.getProduct().getId().equals(p.getId())){
				res.add(b);
			}
		}
		return res;
	}

	protected List<Batch> getBatchPrice(double price){
		List<Batch> res = new ArrayList<>();
		for(Batch b: _allBatches){
			if(b.getPrice() <= price && b.getQuantity() > 0){
				res.add(b);
			}
		}
		return res;
	}
	
	/**
   * 
   * @return List that contains all the batchs
   */
	protected List<Batch> getAllBatchs(){
		List<Batch> res = new ArrayList<>();
		for(Batch b: this._allBatches){
			if(b.getQuantity() != 0){
				res.add(b);
			}
		}
		return res;
	}
	
	/**
   	* @param partner
   	* @param product
   	* @param quantiy
   	* @param price
   	* @return
   	*/
	protected boolean registerBatch(Partner partner, Product product, int quantity, double price){
		Batch b = new Batch(partner, product, quantity, price, _batchId);
		if(_allBatches.add(b)){
			product.setMaxPrice(price);
			product.addQuantity(quantity);
			_batchId++;
			return true;
		}
		return false;
	}

	protected void removeQuantityBatch(int id, int quantity){
		getBatch(id).removeQuantity(quantity);
	}

	//Date
	/**
	*Gets the current date of the Warehouse
   	* @return the current date
   	*/
	protected int getCurrentDay(){
		return _date.getDays();
	}
	
	/**
	*Increases the current date by the number of given days
   	* @param day
   	*/
	protected void advanceCurrentDay(int day){
		_date.add(day);
	}
	


	//Balance
	protected double getCurrentBalance(){
		return _availableBalance;
	}

	protected double getContabilisticBalance(){
		return _contabilisticBalance;
	}
  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException {
	Parser parser = new Parser(this);
	parser.parseFile(txtfile);
  }

}
