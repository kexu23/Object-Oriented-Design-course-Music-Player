package command;

import model.Album;
import view.MusicOrganizerWindow;

public class RemoveSubAlbumCommand implements Command {
	
	Album parentAlbum;
	MusicOrganizerWindow view;
	
	public RemoveSubAlbumCommand(Album parentAlbum, MusicOrganizerWindow view) {
		this.parentAlbum = parentAlbum;
		this.view = view;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		parentAlbum.removeSubAlbum(parentAlbum);
		view.onAlbumRemoved();
		view.displayMessage("Album successfully deleted! :)");
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

}
