package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import ggc.app.exception.InvalidDateException;
import ggc.core.WarehouseManager;


/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

	Form _form = new Form();
	
	DoAdvanceDate(WarehouseManager receiver) {
		super(Label.ADVANCE_DATE, receiver);
		_form.addIntegerField("days","Número de dias a avançar: ");
	}

	@Override
	public final void execute() throws CommandException {
		_form.parse();
		int days = _form.integerField("days");
		if (days < 0) {
			throw new InvalidDateException(days);
		}
		_receiver.advanceCurrentDay(days);
	}
}