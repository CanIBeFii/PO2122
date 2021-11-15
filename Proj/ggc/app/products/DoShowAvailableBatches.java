package ggc.app.products;

import java.util.Comparator;
import java.util.List;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.Batch;
import ggc.core.WarehouseManager;


/**
 * Show available batches.
 */
class DoShowAvailableBatches extends Command<WarehouseManager> {

  DoShowAvailableBatches(WarehouseManager receiver) {
    super(Label.SHOW_AVAILABLE_BATCHES, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    List<Batch> b = _receiver.getAllBatchs();
    
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
    
    b.sort(comparator);
    
    for(Batch batch: b){
      _display.addLine(batch.toString());
    }
    _display.display();
    
  }

}
