package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.AddSoundClips;
import model.AddSubAlbum;
import model.Album;
import model.Command;
import model.CommandManager;
import model.RemoveSoundClips;
import model.RemoveSubAlbum;
import model.SoundClip;
import model.SoundClipBlockingQueue;
import model.SoundClipLoader;
import model.SoundClipPlayer;
import view.MusicOrganizerWindow;
import model.CommandStack;


public class MusicOrganizerController {

	private MusicOrganizerWindow view;
	private SoundClipBlockingQueue queue;
	private Album root;
	private CommandStack stack;
	CommandManager manager = CommandManager.getInstance();
	Album album;
	
	public MusicOrganizerController() {

		// TODO: Create the root album for all sound clips
		root = new Album("All Sound Clips");
		
		
		// Create the blocking queue
		queue = new SoundClipBlockingQueue();
				
		// Create a separate thread for the sound clip player and start it
		
		(new Thread(new SoundClipPlayer(queue))).start();
		
		stack = new CommandStack<AddSubAlbum>();
	}
	
	/**
	 * Load the sound clips found in all subfolders of a path on disk. If path is not
	 * an actual folder on disk, has no effect.
	 */
	public Set<SoundClip> loadSoundClips(String path) {
		Set<SoundClip> clips = SoundClipLoader.loadSoundClips(path);
		// TODO: Add the loaded sound clips to the root album
		root.addSongs(clips);
		return clips;
	}
	
	public void registerView(MusicOrganizerWindow view) {
		this.view = view;
	}
	
	/**
	 * Returns the root album
	 */
	public Album getRootAlbum(){
		return root;
	}
	
	public void undo() {
		manager.undo();
	}
	
	public void redo() {
		manager.redo();
	}

	/**
	 * Adds an album to the Music Organizer
	 */
	public void addNewAlbum(Album parentAlbum){ //TODO Update parameters if needed - e.g. you might want to give the currently selected album as parameter

	        String name = view.promptForAlbumName();
	        Album album = null;
	        if(name != null && name != "") {
	            album = new Album(name);
	        }
	        else if(name == "") {
	            view.displayMessage("Album name can't be empty!");
	        }
	        
	        Command c = new AddSubAlbum(parentAlbum, view, album);
	        manager.execute(c);

	    }

        //List<Command> actionList = new ArrayList<>();
        //actionList.add(new AddSubAlbum(parentAlbum,view));
        //manager.execute(actionList);
        //AddSubAlbum album = new AddSubAlbum(parentAlbum,view);
        //album.execute();
        //stack.push(album);

    

    /**
     * Removes an album from the Music Organizer
     */
    public void deleteAlbum(Album parentAlbum){ //TODO Update parameters if needed
    	Command c = new RemoveSubAlbum(parentAlbum, view);
    	manager.execute(c);

        //List<Command> actionLists = new ArrayList<>();
        //actionLists.add(new RemoveSubAlbum(parentAlbum,view));
        //manager.execute(actionLists);
        //RemoveSubAlbum album = new RemoveSubAlbum(parentAlbum,view);
        //album.execute();
        //stack.push(album);
    }
	
	/**
	 * Adds sound clips to an album
	 */
	public void addSoundClips(Album parentAlbum, List<SoundClip> list){ //TODO Update parameters if needed
		AddSoundClips clips = new AddSoundClips(parentAlbum, list,view);
		manager.execute(clips);
	}
	
	/**
	 * Removes sound clips from an album
	 */
	public void removeSoundClips(Album parentAlbum, List<SoundClip> list){ //TODO Update parameters if needed
		RemoveSoundClips clips = new RemoveSoundClips(parentAlbum, list,view);
		manager.execute(clips);
	}
	
	/**
	 * Puts the selected sound clips on the queue and lets
	 * the sound clip player thread play them. Essentially, when
	 * this method is called, the selected sound clips in the 
	 * SoundClipTable are played.
	 */
	public void playSoundClips(){
		List<SoundClip> l = view.getSelectedSoundClips();
		queue.enqueue(l);
		for(int i=0;i<l.size();i++) {
			view.displayMessage("Playing " + l.get(i));
		}
	}
}
