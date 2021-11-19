package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;

import java.util.ArrayList;
import java.util.List;

import ggc.app.exception.UnavailableProductException;

import ggc.core.Partner;
import ggc.core.Product;
import ggc.core.Batch;
import ggc.core.Component;

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
        part.toggleProductNotifications(p);
        p.notificateObserver("NEW", p, price);
        part.toggleProductNotifications(p);
        _receiver.registerAcquisition(p, quantity, price, part);
      }
      else{
        Form _recipeForm =  new Form();
        _recipeForm.addIntegerField("numComp", Message.requestNumberOfComponents());
        _recipeForm.addRealField("agrav", Message.requestAlpha());
        _recipeForm.parse();
        int numComp = _recipeForm.integerField("numComp");
        double agrav = _recipeForm.realField("agrav");
        List<Component> Comps = new ArrayList<>();
        for(int i = 0; i < numComp; i++){
          Form compForm = new Form();

          compForm.addStringField("nomeComp", Message.requestProductKey());
          compForm.addIntegerField("quant", Message.requestAmount());
          compForm.parse();

          int quant = compForm.integerField("quant");
          String prodName = compForm.stringField("nomeComp");

          Product por = _receiver.getProduct(prodName);
          if(por == null){
            throw new UnknownProductKeyException(prodName);
          }
          Comps.add(new Component(quant, por));
        }
        _receiver.registerRecipe(agrav, Comps, idProd);
        _receiver.registerProduct(idProd, _receiver.getRecipe(idProd));
        Product po = _receiver.getProduct(idProd);
        part.toggleProductNotifications(po);
        po.notificateObserver("NEW", po, price);
        part.toggleProductNotifications(po);
        _receiver.registerAcquisition(po, quantity, price, part);
      }
      
    }

    else{
      Product pro = _receiver.getProduct(idProd);
      boolean bargain = true;
      for(Batch bct: _receiver.getBatchProduct(pro)){
        if(bct.getPrice() <= price){
          bargain = false;
        }
      }
      if(bargain && pro.getQuantity() > 0){
        pro.notificateObserver("BARGAIN", pro, price);
      }
      if(pro.getQuantity() == 0){
        pro.notificateObserver("NEW", prod, price);
      }
      if (!(_receiver.registerAcquisition(pro, quantity, price, part))){
        throw new UnavailableProductException(idProd, 1, 1);
      }
    }
  }

}
