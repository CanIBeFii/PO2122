package ggc.app.partners;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Acquisition;

import java.util.*;

import ggc.app.exception.UnknownPartnerKeyException;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  Form _form = new Form();

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    _form.addStringField("Partner", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    _form.parse();
    String idPartner = _form.stringField("Partner");

    if (_receiver.getPartner(idPartner) == null){
      throw new UnknownPartnerKeyException(idPartner);
    }

    List<Acquisition> acquisitions = _receiver.getPartnerAcquisitions(_receiver.getPartner(idPartner));

    for(Acquisition a: acquisitions){
      _display.addLine(a.toString());
    }
    _display.display();
  }

}
