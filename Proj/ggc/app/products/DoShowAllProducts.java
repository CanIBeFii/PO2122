package ggc.app.products;

import java.util.Comparator;
import java.util.List;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.Product;
import ggc.core.WarehouseManager;

/**
 * Show all products.
 */
class DoShowAllProducts extends Command<WarehouseManager> {

  DoShowAllProducts(WarehouseManager receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    List<Product> p = _receiver.getAllProducts();
    
    Comparator<Product> comparator = new Comparator<Product>() {
      public int compare(Product a, Product b) {
        return (a.getId()).toUpperCase().compareTo(b.getId().toUpperCase());
      }
    };
    
    p.sort(comparator);
    
    for(Product product: p){
      _display.addLine(product.toString());
    }
    _display.display();
    
  }

}