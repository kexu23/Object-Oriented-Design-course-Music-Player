package model;

import view.MusicOrganizerWindow;

public class RemoveSubAlbum implements Command{
	
	Album parentAlbum;
	Album album;
	MusicOrganizerWindow view;
	
	public RemoveSubAlbum(Album album, MusicOrganizerWindow view) {
		this.album = album;
		this.parentAlbum = album.getParentAlbum();
		this.view = view;
	}

	@Override
	public void execute() {
		parentAlbum.removeSubAlbum(album);
		view.onAlbumRemoved(album);
		view.displayMessage("Album successfully deleted");
	}

	@Override
	public void undo() {
        parentAlbum.addSubAlbum(album);
        view.onAlbumAdded(parentAlbum,album);
        updateAlbum(album);
        
	}
	private void updateAlbum(Album alb) {
		for(Album a:alb.getSubAlbums()) {
			view.onAlbumAdded(alb, a);
			updateAlbum(a);
		}
	}
}
