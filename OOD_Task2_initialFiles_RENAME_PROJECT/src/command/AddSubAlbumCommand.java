package command;

import model.Album;
import view.MusicOrganizerWindow;

public class AddSubAlbumCommand implements Command{
	
	Album parentAlbum;
	MusicOrganizerWindow view;
	
	public AddSubAlbumCommand(Album parentAlbum, MusicOrganizerWindow view) {
		this.parentAlbum = parentAlbum;
		this.view = view;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		String name = view.promptForAlbumName();
		if(name != null && name != "") {
			Album a = new Album(name);
	
			parentAlbum.addSubAlbum(a);
			view.onAlbumAdded(a);
		}
		else if(name == "") {
			view.displayMessage("Album name can't be empty!");
		}
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	
}
