package models.utils;

public interface Originator {

    void restore(Memento memento);

    Memento createMemento();

}
