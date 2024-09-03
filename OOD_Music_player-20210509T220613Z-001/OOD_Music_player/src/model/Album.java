package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;


public class Album{
   
private String albumName;       // Creates the album name, set for songs, and a list for subalbums
private Set<SoundClip> songs;
private Set<Album> subalbum;
private Album parentAlbum;		// Creates the parent album


	private boolean invariant() {
		if(albumName == null || songs == null || subalbum == null)
			return false;
		
		if(albumName == "")
			return false;
		
		if (parentAlbum != null)
			if(!parentAlbum.contains(this))
				return false;
		
		for(Album a : subalbum) {
			if(!this.contains(a.getAllSongs())) return false;
		}
		return true;
	}
	
	public Album(String name){  // Constructor for the Album
		assert name!=null && name!="";
        this.albumName = name;
        songs = new HashSet<SoundClip>();
        subalbum = new LinkedHashSet<Album>();
        
        assert invariant();
    }
	
	@Override
    public String toString() {
    	return albumName;
    }	
	
	public String getAlbumName(){               // Returns a specific album name
        return albumName;
    }
	
	public void setAlbumName(String s) {
		assert s!=null && s!="";
		albumName = s;
		assert invariant();
	}
	
	public Album getParentAlbum() {
		return parentAlbum;
	}
	
	public Set<Album> getSubAlbum(Album album){           // Returns the list of subalbums in a specific album
        return subalbum;    
    }
	
	public boolean contains(Album a) {
		return subalbum.contains(a);
	}
	
	public void addSubAlbum(Album a){					// Adds a subalbum into an album
		assert a!=null && !this.contains(a);
		assert invariant();
		
		if (a.parentAlbum!=null) {
			a.parentAlbum.removeSubAlbum(a);
		}
		
		subalbum.add(a);
		a.parentAlbum = this;
		
		assert invariant();
		assert this.contains(a) && a.getParentAlbum() == this;
    }
	
	public void removeSubAlbum(Album a){    // Removes a subalbum from an album
        assert a!=null && this.contains(a);
        assert invariant();
        
        subalbum.remove(a);
        a.parentAlbum = null;
        
        assert invariant();
        assert !this.contains(a) && a.getParentAlbum() == null;
    }
	
	public void removeFromParent() {
		assert invariant();
		assert parentAlbum != null;
		parentAlbum.removeSubAlbum(this);
		assert invariant();
	}
	
	public boolean transitivelyContainsAlbum(Album a) {
		if(subalbum.contains(a)) {
			return true;
		}
		return false;
	}
	
    public void addSongs(Set<SoundClip> s){    	// Adds a song to an album and parentalbums
    	assert s != null;
    	
        songs.addAll(s);
        if(parentAlbum != null){
            parentAlbum.addSongs(s);
            
        assert invariant();
        }
    }
        
    public Set<SoundClip> getAllSongs() {
    	Set<SoundClip> scs = new HashSet<SoundClip>();
        scs.addAll(songs);
        return scs;
    }
        
    public void addSong(SoundClip s) {
        assert s != null;
        	
        Set<SoundClip> scs = new HashSet<SoundClip>();
        scs.add(s);
        addSongs(scs);
        	
        assert invariant();
        assert this.contains(s);
    }
        
    public void removeSong(SoundClip s) {
        assert s != null && this.contains(s);
        assert invariant();
        	
        Set<SoundClip> scs = new HashSet<SoundClip>();
        scs.add(s);
        removeSongs(scs);
        
        assert invariant();
        assert !this.contains(s);
        }
    
    public void removeSongs(Set<SoundClip> s) {
    	assert s != null;
    	assert invariant();
    	
    	for (Album c: subalbum)
    		c.removeSongs(s);
    	
    	songs.removeAll(s);
    	
    	assert invariant();
    	assert !this.contains(s);
    }
    public Set<Album> getSubAlbums() {
    	return subalbum;
    }
        
    public boolean contains(Set<SoundClip> setlist) {
        return getAllSongs().containsAll(setlist);
    }
        
    public boolean contains(SoundClip s) {
        return getAllSongs().contains(s);
    }
        
    public Iterator<Album> albumIterator() {
        return subalbum.iterator();
    }
        
}