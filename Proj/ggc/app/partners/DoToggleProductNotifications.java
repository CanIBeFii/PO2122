package ggc.app.partners;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Partner;
import ggc.core.Product;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
//FIXME import classes

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  Form _form = new Form();

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    _form.addStringField("idPart", Message.requestPartnerKey());
    _form.addStringField("idProd", Message.requestProductKey());
  }

  @Override
  public void execute() throws CommandException {
    _form.parse();
    String idPart = _form.stringField("idPart");
    String idProd = _form.stringField("idProd");

    Partner part = _receiver.getPartner(idPart);
    if (part == null){
      throw new UnknownPartnerKeyException(idPart);
    }

    Product prod = _receiver.getProduct(idProd);
    if (prod == null){
      throw new UnknownProductKeyException(idProd);
    }

    _receiver.toggleProductNotifications(prod, part);
  }

}
