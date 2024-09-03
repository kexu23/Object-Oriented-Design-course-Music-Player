package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Album;
import model.SoundClip;
import view.MusicOrganizerWindow;

public class RemoveSoundClips implements Command{
	
	Album album;
	Album parentAlbum;
	MusicOrganizerWindow view;
	SoundClip soundclip;
	List<SoundClip> list;
	Map<Album, Set<SoundClip>> map;
		
	public RemoveSoundClips(Album parentAlbum, List<SoundClip> list, MusicOrganizerWindow view) {
		this.parentAlbum = parentAlbum;
		this.list = list;
		this.view = view;
		map = new HashMap<>();
	}

	@Override
	public void execute() {
		updateremovedsongs(parentAlbum);
		Set<SoundClip> setList = new HashSet<>(list);
		parentAlbum.removeSongs(setList);
		view.onClipsUpdated();
	}

	@Override
	public void undo() {
		for(Album a : map.keySet()) {
			a.addSongs(map.get(a));
		}
		view.onClipsUpdated();
	}
	
	private void updateremovedsongs(Album album) {  // album, list, diff
		Set<SoundClip> diff = new HashSet<>(list); // diff contains the songs that are going to be removed
		diff.removeAll(album.getAllSongs());
		map.put(album, diff);
		
		for (Album a : album.getSubAlbums()) {
			updateremovedsongs(a);
		}
	}

}
