package models;

import models.utils.Memento;
import models.utils.Originator;

public class Game implements Originator {

	public boolean isBlocked(){
		return true;
	}

    @Override
    public Memento createMemento() {
        GameMemento gameMemento = new GameMemento();
        return gameMemento;
    }

    @Override
    public void restore(Memento memento) {
        GameMemento gameMemento = (GameMemento) memento;
    }

}
