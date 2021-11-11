package ggc.app.partners;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Sale;

import java.util.*;

import ggc.app.exception.UnknownPartnerKeyException;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerSales extends Command<WarehouseManager> {

	Form _form= new Form();
  DoShowPartnerSales(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_SALES, receiver);
    _form.addStringField("Partner", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    _form.parse();
    String idPartner = _form.stringField("Partner");
    
    if( _receiver.getPartner(idPartner) == null){
    	throw new UnknownPartnerKeyException(idPartner);
  	}
  	
  	List<Sale> sales = _receiver.getPartnerSales(_receiver.getPartner(idPartner));
  	
  	for(Sale s: sales){
		_display.addLine(s.toString());
  	}
  	_display.display();
  }

}