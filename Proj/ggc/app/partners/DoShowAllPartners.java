package ggc.app.partners;

import java.util.Comparator;
import java.util.List;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.Partner;
import ggc.core.WarehouseManager;

/**
 * Show all partners.
 */
class DoShowAllPartners extends Command<WarehouseManager> {

  public DoShowAllPartners(WarehouseManager receiver) {
    super(Label.SHOW_ALL_PARTNERS, receiver);
  }

  @Override
  public void execute() throws CommandException {
    List<Partner> partners = _receiver.getAllPartners();
    
    Comparator<Partner> comparator = new Comparator<Partner>() {
    public int compare(Partner a, Partner b) {
		return (a.getId().toUpperCase()).compareTo(b.getId().toUpperCase());
      }
    };
	  partners.sort(comparator);
	
	  for (Partner partner: partners){
		  _display.addLine(partner.toString());
	  }
	  _display.display();
	}
}