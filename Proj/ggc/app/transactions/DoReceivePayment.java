package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.Transaction;
import ggc.core.WarehouseManager;

import ggc.app.exception.UnknownTransactionKeyException;

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    _form.addIntegerField("idTrans", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    _form.parse();
    int idTrans = _form.integerField("idTrans");
    Transaction t = _receiver.getTransaction(idTrans);

    if(t == null){
      throw new UnknownTransactionKeyException(idTrans);
    }

    if(t.getType().equals("SaleByCredit") && !t.isPaid()){
      t.setPaymentDate(_receiver.getCurrentDate());
    } 
  }

}
