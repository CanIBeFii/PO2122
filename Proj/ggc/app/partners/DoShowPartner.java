package ggc.app.partners;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.Notification;
import ggc.core.Partner;
import ggc.core.WarehouseManager;
import ggc.app.exception.UnknownPartnerKeyException;


/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  Form _form = new Form();

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    _form.addStringField("partnerId", Message.requestPartnerKey());  
  }

  @Override
  public void execute() throws CommandException {
    _form.parse();
    String partnerId = _form.stringField("partnerId");
    if (_receiver.getPartner(partnerId) == null){
      throw new UnknownPartnerKeyException(partnerId);
    }
    Partner p = _receiver.getPartner(partnerId);
	  _display.addLine(p.toString());
    for(Notification n: p.getNotifications()){
      if(!n.hasNotify()){
        _display.addLine(n.toString());
        n.toggleNotify();
      }
    }
	  _display.display();
	}
}