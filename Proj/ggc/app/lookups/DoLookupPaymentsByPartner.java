package ggc.app.lookups;

import ggc.app.exception.UnknownPartnerKeyException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.core.Partner;
import ggc.core.Transaction;


/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    _form.addStringField("idPart", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    _form.parse();
    String idPart = _form.stringField("idPart");

    Partner part = _receiver.getPartner(idPart);
    if(part == null){
      throw new UnknownPartnerKeyException(idPart);
    }

    for(Transaction t: _receiver.getPartnerSales(part)){
      if(t.isPaid() || t.getType().equals("BreakdownSale")){
        _display.addLine(t.toString());
      }
    }
    _display.display();
  }

}
