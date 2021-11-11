package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.DuplicatePartnerKeyException;
import ggc.core.WarehouseManager;


/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {
	Form _form = new Form();

	public DoRegisterPartner(WarehouseManager receiver) {
		super(Label.REGISTER_PARTNER, receiver);
		_form.addStringField("id", Message.requestPartnerKey());
		_form.addStringField("name", Message.requestPartnerName());
		_form.addStringField("address", Message.requestPartnerAddress());
	}

	@Override
	public void execute() throws CommandException, DuplicatePartnerKeyException{
		_form.parse();
		String id = _form.stringField("id");
		String name = _form.stringField("name");
		String address = _form.stringField("address");

		if(_receiver.getPartner(id) != null){
			throw new DuplicatePartnerKeyException(id);
		}
		if ( _receiver.registerPartner(name, address, id) == false ){
			throw new UnknownPartnerKeyException(id);
		}
	}
} 