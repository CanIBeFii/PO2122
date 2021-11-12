package ggc.core;


import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import ggc.core.Acquisition;
import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
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

  public Recipe getRecipe(String name){
		return _warehouse.getRecipe(name);
	}

	public List<Recipe> getAllRecipfilees(){
		return _warehouse.getAllRecipes();
	}

	public boolean registerRecipe(double alpha, List<Component> components, String name){
		return _warehouse.registerRecipe(alpha, components, name);
	}

	public Partner getPartner(String id){
		return _warehouse.getPartner(id);
	}

	public List<Partner> getAllPartners(){
		return _warehouse.getAllPartners();
	}

	public boolean registerPartner(String name, String adress, String id){
		return _warehouse.registerPartner(name, adress, id);
	}

	public Product getProduct(String id){
		return _warehouse.getProduct(id);
	}
	
	public List<Product> getAllProducts(){
		return _warehouse.getAllProducts();
	}

	public boolean registerProduct(String id){
		return _warehouse.registerProduct(id);
	}

	public boolean registerProduct(String id, Recipe recipe){
		return _warehouse.registerProduct(id, recipe);
	}

	public Transaction getTransaction(int id){
		return _warehouse.getTransaction(id);
	}
	
	public List<Transaction> getAllTransactions(){
		return _warehouse.getAllTransactions();
	}

	public List<Acquisition> getPartnerAcquisitions(Partner p){
		return _warehouse.getPartnerAcquisitions(p);
	}

	public List<Sale> getPartnerSales(Partner p){
		return _warehouse.getPartnerSales(p);
	}

	public boolean registerAcquisition(Product product, int quantity, double price, Partner part){
		return _warehouse.registerAcquisition(product, quantity, price, part);
	}

	public boolean registerBreakdownSale(Product product, int quantity, double price, Partner part, int id){
		return _warehouse.registerBreakdownSale(product, quantity, price, part);
	}

	public boolean registerSaleByCredit(Product p,int quantity, Partner part, int deadline){
		return _warehouse.registerSaleByCredit(p, quantity, part, deadline);
	}

	public int getCurrentDay(){
		return _warehouse.getCurrentDay();
	}

	public void advanceCurrentDay(int day){
		_warehouse.advanceCurrentDay(day);
	}

	public Batch getBatch(int id){
		return _warehouse.getBatch(id);
	}

	public List<Batch> getBatchPartner(Partner p){
		return _warehouse.getBatchPartner(p);
	}

	public List<Batch> getBatchProduct(Product p){
		return _warehouse.getBatchProduct(p);
	}

	public List<Batch> getBatchPrice(double price){
		return _warehouse.getBatchPrice(price);
	}

	public List<Batch> getAllBatchs(){
		return _warehouse.getAllBatchs();
	}

	public boolean registerBatch(Partner partner, Product product, int quantity, double price){
		return _warehouse.registerBatch(partner, product, quantity, price);
	}

	public double getCurrentBalance(){
		return _warehouse.getCurrentBalance();
	}

	public double getContabilisticBalance(){
		return _warehouse.getContabilisticBalance();
	}

	public boolean toggleProductNotifications(Product prod, Partner part){
		return _warehouse.toggleProductNotifications(prod, part);
	}

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
