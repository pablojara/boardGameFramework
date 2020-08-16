package models.utils;

public interface Originator {

    void restore(Memento memento) throws Exception;

    Memento createMemento() throws Exception;

}
