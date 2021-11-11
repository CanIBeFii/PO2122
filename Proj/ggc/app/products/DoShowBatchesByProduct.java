package ggc.app.products;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Batch;

import java.util.*;
import ggc.app.exception.UnknownProductKeyException;
//FIXME import classes

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {

  Form _form = new Form();

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    _form.addStringField("Product", Message.requestProductKey());
  }

  @Override
  public final void execute() throws CommandException {
    _form.parse();
    String idProduct = _form.stringField("Product");

    if (_receiver.getProduct(idProduct) == null){
      throw new UnknownProductKeyException(idProduct);
    }

    List<Batch> batches = _receiver.getBatchProduct(_receiver.getProduct(idProduct));

    Comparator<Batch> comparator = new Comparator<Batch>() {
      public int compare(Batch a, Batch b) {
        if(a.getProduct().getId().equals(b.getProduct().getId())){
          if(a.getPartner().getId().equals(b.getPartner().getId())){
            if(a.getPrice() == b.getPrice()){
              return a.getQuantity() - b.getQuantity();
            }
            if((int)Math.round(a.getPrice()) < (int)Math.round(b.getPrice())){
              return -(int)Math.round(a.getPrice()) - (int)Math.round(b.getPrice());
            }
            return ((int)Math.round(a.getPrice()) - (int)Math.round(b.getPrice()));
          }
          return a.getPartner().getId().compareTo(b.getPartner().getId());
        }
        return a.getProduct().getId().compareTo(b.getProduct().getId());
      }
    };

    batches.sort(comparator);

    for(Batch b: batches){
      if(b.getQuantity() > 0)
        _display.addLine(b.toString()); 
    }
    _display.display();
  }

}
