package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CommandStack<T> {
	
	private List<T> dataCollection;

    public CommandStack() {
        dataCollection = new LinkedList<>();
    }

    public void push(T item) {
        dataCollection.add(0, item);
        
    }

    public Optional<T> pop() {
        if(dataCollection.size() > 0)
            return Optional.of(dataCollection.remove(dataCollection.size() - 1));
        else
            return Optional.empty();
    }

    public void clear() {
        dataCollection.clear();
    }
    
    public T getLast() {
    	return dataCollection.get(dataCollection.size()-1);
    }

}
