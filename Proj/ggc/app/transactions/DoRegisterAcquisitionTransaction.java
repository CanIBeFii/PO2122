package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnavailableProductException;

import ggc.core.Partner;
import ggc.core.Product;
//FIXME import classes

/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    _form.addStringField("idPart", Message.requestPartnerKey());
    _form.addStringField("idProd", Message.requestProductKey());
    _form.addIntegerField("price", Message.requestPrice());
    _form.addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    _form.parse();
    String idPart = _form.stringField("idPart");
    String idProd = _form.stringField("idProd");
    int price = _form.integerField("price");
    int quantity = _form.integerField("quantity");

    Partner part = _receiver.getPartner(idPart);
    if (part == null){
      throw new UnknownPartnerKeyException(idPart);
    }
    
    Product prod = _receiver.getProduct(idProd);
    if (prod == null){
      Form _newForm = new Form();
      _newForm.addStringField("response", Message.requestAddRecipe());
      _newForm.parse(); 
      String response = _newForm.stringField("response");
      if(response.equals("n")){
        _receiver.registerProduct(idProd);
        Product p = _receiver.getProduct(idProd);
        _receiver.registerAcquisition(p, quantity, price, part);
      }
    }


    else{
      Product pro = _receiver.getProduct(idProd);
    //Verificar quantidade yhyh do it e preco!!!!!!!
      if (!(_receiver.registerAcquisition(prod, quantity, price, part))){
        throw new UnavailableProductException(idProd, 1, 1);
      }
    }
  }

}
