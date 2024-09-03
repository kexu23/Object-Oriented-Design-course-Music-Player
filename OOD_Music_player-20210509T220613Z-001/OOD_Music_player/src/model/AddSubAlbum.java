package model;

import view.MusicOrganizerWindow;
import model.Album;

public class AddSubAlbum implements Command {
	
	Album album;
	Album parentAlbum;
	MusicOrganizerWindow view;
	
	public AddSubAlbum(Album parentAlbum, MusicOrganizerWindow view, Album album) {
		this.parentAlbum = parentAlbum;
		this.view = view;
		this.album = album;
	}

	@Override
	public void execute() {
		parentAlbum.addSubAlbum(album);
        view.onAlbumAdded(parentAlbum,album);
    }
        
	

	@Override
	public void undo() {
		parentAlbum.removeSubAlbum(album);
		view.onAlbumRemoved(album);
	}
	
}
