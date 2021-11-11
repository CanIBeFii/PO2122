package ggc.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.FileOpenFailedException;
import pt.tecnico.uilib.forms.Form;
import ggc.core.WarehouseManager;
import ggc.core.exception.MissingFileAssociationException;
import ggc.core.exception.UnavailableFileException;


/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {

	Form _form = new Form();
  
  /** @param receiver */
  public DoOpenFile(WarehouseManager receiver) {
    super(Label.OPEN, receiver);
    _form.addStringField("fileName", Message.openFile());
  }

  @Override
  public final void execute() throws CommandException{
    _form.parse();
    String fileName = _form.stringField("fileName");
    try {
      _receiver.load(fileName);
    }

    catch (FileNotFoundException c) {
      	throw new FileOpenFailedException(fileName);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    
  }

}