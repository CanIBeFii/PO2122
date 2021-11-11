package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.InvalidDateException;
import ggc.app.exception.UnavailableProductException;

import ggc.core.Partner;
import ggc.core.Product;

/**
 * 
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    _form.addStringField("idPart", Message.requestPartnerKey());
    _form.addIntegerField("deadline", Message.requestPaymentDeadline());
    _form.addStringField("idProd", Message.requestProductKey());
    _form.addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    _form.parse();
    String idPart = _form.stringField("idPart");
    String idProd = _form.stringField("idProd");
    int deadline = _form.integerField("deadline");
    int quantity = _form.integerField("quantity");

    Partner part = _receiver.getPartner(idPart);
    if (part == null){
      throw new UnknownPartnerKeyException(idPart);
    }
    Product prod = _receiver.getProduct(idProd);
    if (prod == null){
      throw new UnknownProductKeyException(idProd);
    }
    if (_receiver.getCurrentDay() > deadline){
      throw new InvalidDateException(deadline);
    }
    /*Fazer verificar quantidade !!!!!!!!!!!!!!!!!!!!!!!!!!*/
    if (!(_receiver.registerSaleByCredit(prod, quantity, part, deadline))){
      throw new UnavailableProductException(idProd, 1, 1);
      /*Ver qual a exception!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      MUdar valores dentro de UNAVAIABLEpRODUCTeXCEPTION
      
      IMportante ver isto senão dá .....*/
    }
  }

}