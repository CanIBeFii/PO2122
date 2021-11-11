package ggc.app.transactions;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.exception.UnknownTransactionKeyException;
//FIXME import classes

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<WarehouseManager> {

  Form _form = new Form();

  public DoShowTransaction(WarehouseManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    _form.addIntegerField("id", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    _form.parse();
    int id = _form.integerField("id");

    if(_receiver.getTransaction(id) == null){
      throw new UnknownTransactionKeyException(id);
    }
    _display.addLine(_receiver.getTransaction(id).toString());
    _display.display();
  }

}
