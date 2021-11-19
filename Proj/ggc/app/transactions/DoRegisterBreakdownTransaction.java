package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;
import ggc.core.Product;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnavailableProductException;

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    _form.addStringField("idPart", Message.requestPartnerKey());
    _form.addStringField("idProd", Message.requestProductKey());
    _form.addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    _form.parse();
    String idPart = _form.stringField("idPart");
    String idProd = _form.stringField("idProd");
    int quantity = _form.integerField("quantity");

    Partner part = _receiver.getPartner(idPart);
    if(part == null){
      throw new UnknownPartnerKeyException(idPart);
    }

    Product prod = _receiver.getProduct(idProd);
    if(prod == null){
      throw new UnknownProductKeyException(idProd);
    }

    if (prod.getQuantity() < quantity){
      throw new UnavailableProductException(idProd, quantity, prod.getQuantity());
    }

    if(prod.getType().equals("AggregateProduct")){
      _receiver.registerBreakdownSale(prod, quantity, part);
    }
  }

}
