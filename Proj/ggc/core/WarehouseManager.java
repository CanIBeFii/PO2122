package ggc.core;


import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.MissingFileAssociationException;

import java.util.*;

/** Façade for access. */
public class WarehouseManager implements Serializable{

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The wharehouse itself. */
  private Warehouse _warehouse;

  public WarehouseManager(){
	  _warehouse = new Warehouse();
  }

  
  /** 
   * @param name
   * @return Recipe
   */
  public Recipe getRecipe(String name){
		return _warehouse.getRecipe(name);
	}

	
	/** 
	 * @return List<Recipe>
	 */
	public List<Recipe> getAllRecipfilees(){
		return _warehouse.getAllRecipes();
	}

	
	/** 
	 * @param alpha
	 * @param components
	 * @param name
	 * @return boolean
	 */
	public boolean registerRecipe(double alpha, List<Component> components, String name){
		return _warehouse.registerRecipe(alpha, components, name);
	}

	
	/** 
	 * @param id
	 * @return Partner
	 */
	public Partner getPartner(String id){
		return _warehouse.getPartner(id);
	}

	
	/** 
	 * @return List<Partner>
	 */
	public List<Partner> getAllPartners(){
		return _warehouse.getAllPartners();
	}

	
	/** 
	 * @param name
	 * @param adress
	 * @param id
	 * @return boolean
	 */
	public boolean registerPartner(String name, String adress, String id){
		return _warehouse.registerPartner(name, adress, id);
	}

	/** 
	 * @param id
	 * @return Product
	 */
	public Product getProduct(String id){
		return _warehouse.getProduct(id);
	}
	
	
	/** 
	 * @return List<Product>
	 */
	public List<Product> getAllProducts(){
		return _warehouse.getAllProducts();
	}

	
	/** 
	 * @param id
	 * @return boolean
	 */
	public boolean registerProduct(String id){
		return _warehouse.registerProduct(id);
	}

	
	/** 
	 * @param id
	 * @param recipe
	 * @return boolean
	 */
	public boolean registerProduct(String id, Recipe recipe){
		return _warehouse.registerProduct(id, recipe);
	}

	
	/** 
	 * @param id
	 * @return Transaction
	 */
	public Transaction getTransaction(int id){
		return _warehouse.getTransaction(id);
	}
	
	
	/** 
	 * @return List<Transaction>
	 */
	public List<Transaction> getAllTransactions(){
		return _warehouse.getAllTransactions();
	}

	
	/** 
	 * @param p
	 * @return List<Acquisition>
	 */
	public List<Acquisition> getPartnerAcquisitions(Partner p){
		return _warehouse.getPartnerAcquisitions(p);
	}

	
	/** 
	 * @param p
	 * @return List<Sale>
	 */
	public List<Sale> getPartnerSales(Partner p){
		return _warehouse.getPartnerSales(p);
	}

	
	/** 
	 * @param product
	 * @param quantity
	 * @param price
	 * @param part
	 * @return boolean
	 */
	public boolean registerAcquisition(Product product, int quantity, double price, Partner part){
		return _warehouse.registerAcquisition(product, quantity, price, part);
	}

	
	/** 
	 * @param product
	 * @param quantity
	 * @param price
	 * @param part
	 * @return boolean
	 */
	public boolean registerBreakdownSale(Product product, int quantity, Partner part){
		return _warehouse.registerBreakdownSale(product, quantity, part);
	}

	
	/** 
	 * @param p
	 * @param quantity
	 * @param part
	 * @param deadline
	 * @return boolean
	 */
	public boolean registerSaleByCredit(Product p,int quantity, Partner part, int deadline){
		return _warehouse.registerSaleByCredit(p, quantity, part, deadline);
	}

	
	/** 
	 * @return int
	 */
	public int getCurrentDay(){
		return _warehouse.getCurrentDay();
	}

	
	/** 
	 * @param day
	 */
	public void advanceCurrentDay(int day){
		_warehouse.advanceCurrentDay(day);
	}

	
	/** 
	 * @param id
	 * @return Batch
	 */
	public Batch getBatch(int id){
		return _warehouse.getBatch(id);
	}

	
	/** 
	 * @param p
	 * @return List<Batch>
	 */
	public List<Batch> getBatchPartner(Partner p){
		return _warehouse.getBatchPartner(p);
	}

	
	/** 
	 * @param p
	 * @return List<Batch>
	 */
	public List<Batch> getBatchProduct(Product p){
		return _warehouse.getBatchProduct(p);
	}

	
	/** 
	 * @param price
	 * @return List<Batch>
	 */
	public List<Batch> getBatchPrice(double price){
		return _warehouse.getBatchPrice(price);
	}

	
	/** 
	 * @return List<Batch>
	 */
	public List<Batch> getAllBatchs(){
		return _warehouse.getAllBatchs();
	}

	
	/** 
	 * @param partner
	 * @param product
	 * @param quantity
	 * @param price
	 * @return boolean
	 */
	public boolean registerBatch(Partner partner, Product product, int quantity, double price){
		return _warehouse.registerBatch(partner, product, quantity, price);
	}

	
	/** 
	 * @return double
	 */
	public double getCurrentBalance(){
		return _warehouse.getCurrentBalance();
	}

	
	/** 
	 * @return Date
	 */
	public Date getCurrentDate(){
		return _warehouse.getCurrentDate();
	}

	
	/** 
	 * @return double
	 */
	public double getContabilisticBalance(){
		return _warehouse.getContabilisticBalance();
	}

	
	/** 
	 * @param prod
	 * @param part
	 * @return boolean
	 */
	public boolean toggleProductNotifications(Product prod, Partner part){
		return _warehouse.toggleProductNotifications(prod, part);
	}

	
	/** 
	 * @return String
	 */
	public String getFileName(){
		return _filename;
	}

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    if(_filename == ""){
		throw new MissingFileAssociationException();
	}
	try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(_filename))){
		out.writeObject(_warehouse);
	}
  }
  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
	_filename = filename;
    save();
  }

  /**
   * @@param filename
   * @@throws UnavailableFileException
   */
  public void load(String filename) throws /*UnavailableFileException,*/ ClassNotFoundException, FileNotFoundException{
    try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename))){
		_filename = filename;
		_warehouse = (Warehouse)input.readObject();
	}catch(IOException c){
		throw new FileNotFoundException();
	}
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(textfile, e);
    }
  }

}
