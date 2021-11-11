package ggc.app.main;

import java.io.IOException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import ggc.core.WarehouseManager;
import ggc.core.exception.MissingFileAssociationException;


/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

	private Form _form = new Form();

  /** @param receiver */
  public DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
  }

@Override
  public final void execute() throws CommandException {
    String fileName = _receiver.getFileName();
    if(fileName.equals("")){
      _form.addStringField("saveFile",Message.newSaveAs());
      _form.parse();
      fileName = _form.stringField("saveFile");
    }
    try {
      _receiver.save();
    } catch (MissingFileAssociationException a){
      try {
        try {
          _receiver.saveAs(fileName);
        } catch (MissingFileAssociationException b) {
          b.printStackTrace();
        }
      } catch(IOException c){
        c.printStackTrace();
      }
    } catch (IOException d) {
      d.printStackTrace();
      }
  }
}