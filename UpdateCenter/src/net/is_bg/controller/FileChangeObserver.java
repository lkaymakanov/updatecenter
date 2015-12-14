package net.is_bg.controller;

import java.util.Observable;
import java.util.Observer;

public abstract class FileChangeObserver<T>  implements  Observer{
	public abstract void update(T event);
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		update((T)arg);
	}
}
