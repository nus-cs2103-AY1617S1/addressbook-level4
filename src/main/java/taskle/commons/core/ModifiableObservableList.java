package taskle.commons.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Modifiable view of the obeservable list. Mainly used for the edit command.
 * @author zhiyong
 *
 * @param <E>
 */
public class ModifiableObservableList<E> implements ObservableList<E>{

    private final ObservableList<? extends E> backingList;
    
    public ModifiableObservableList(ObservableList<? extends E> backingList) {
        if (backingList == null) {
            throw new NullPointerException();
        }
        this.backingList = backingList;
    }
    @Override
    public boolean add(E arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void add(int arg0, E arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean addAll(Collection<? extends E> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends E> arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean contains(Object arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public E get(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int indexOf(Object arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int lastIndexOf(Object arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean remove(Object arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public E remove(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public E set(int arg0, E arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<E> subList(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addListener(InvalidationListener arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeListener(InvalidationListener arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean addAll(E... arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addListener(ListChangeListener<? super E> arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void remove(int arg0, int arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean removeAll(E... arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void removeListener(ListChangeListener<? super E> arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean retainAll(E... arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setAll(E... arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setAll(Collection<? extends E> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

}
