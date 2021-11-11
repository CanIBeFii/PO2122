package ggc.app.lookups;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.core.Batch;

import java.util.*;
//FIXME import classes

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    _form.addIntegerField("price", Message.requestPriceLimit());
  }

  @Override
  public void execute() throws CommandException {
    _form.parse();
    int price = _form.integerField("price");
    List<Batch> batchs = _receiver.getBatchPrice(price);

    Comparator<Batch> comparator = new Comparator<Batch>() {
      public int compare(Batch a, Batch b) {
        if(a.getProduct().getId().equals(b.getProduct().getId())){
          if(a.getPartner().getId().equals(b.getPartner().getId())){
            if(a.getPrice() == b.getPrice()){
              return a.getQuantity() - b.getQuantity();
            }
            if((int)Math.round(a.getPrice()) < (int)Math.round(b.getPrice())){
              return (int)Math.round(a.getPrice()) - (int)Math.round(b.getPrice());
            }
            return ((int)Math.round(a.getPrice()) - (int)Math.round(b.getPrice()));
          }
          return a.getPartner().getId().compareTo(b.getPartner().getId());
        }
        return a.getProduct().getId().compareTo(b.getProduct().getId());
      }
    };
    
    batchs.sort(comparator);

    for (Batch b: batchs){
      _display.addLine(b.toString());
    }
    _display.display();
  }

}
