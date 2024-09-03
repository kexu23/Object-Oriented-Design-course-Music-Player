package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Album;
import model.SoundClip;
import view.MusicOrganizerWindow;

public class AddSoundClips implements Command{
	
	Album album;
	Album parentAlbum;
	MusicOrganizerWindow view;
	SoundClip soundclip;
	List<SoundClip> list;
	Map<Album, Set<SoundClip>> addedclips;
	
	public AddSoundClips(Album parentAlbum, List<SoundClip> list, MusicOrganizerWindow view) {
		this.parentAlbum = parentAlbum;
		this.list = list;
		this.view = view;
		addedclips = new HashMap<>();
	}
	
	@Override
	public void execute() {
		updatesoundclips(parentAlbum);
		Set<SoundClip> setList = new HashSet<>(list);
		parentAlbum.addSongs(setList);
		view.onClipsUpdated();
	}

	@Override
	public void undo() {
		for (Album a : addedclips.keySet()) {
			a.removeSongs(addedclips.get(a));
			view.onClipsUpdated();
		}
	}
	
	private void updatesoundclips(Album album) {
		Set<SoundClip> diff = new HashSet<>(list);
		diff.removeAll(album.getAllSongs());
		addedclips.put(album, diff);
		if(album.getParentAlbum() != null) {
			updatesoundclips(album.getParentAlbum());
		}
	}
}

