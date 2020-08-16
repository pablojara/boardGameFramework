package models;

import annotations.MementoField;
import models.utils.Memento;
import models.utils.MementoAttribute;
import models.utils.Originator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import static java.util.Objects.requireNonNull;


public class Game implements Originator {

	public boolean isBlocked(){
		return true;
	}

    @Override
    public Memento createMemento() throws Exception {

        GameMemento gameMemento = new GameMemento();
        HashMap<String, Object> mementoFields = new HashMap<String, Object>();
        Class<?> objectClass = requireNonNull(this).getClass();

        for (Field field : objectClass.getDeclaredFields()) {

            field.setAccessible(true);

            if (field.isAnnotationPresent(MementoField.class)) {

                String annotationValue = field.getAnnotation(MementoField.class).value();
                Class<?> fieldClass = field.get(this).getClass();

                Constructor constructor = fieldClass.getConstructor();
                MementoAttribute mementoAttribute = (MementoAttribute) constructor.newInstance();
                mementoAttribute.initializeMemento((MementoAttribute) field.get(this));
                mementoFields.put(annotationValue, mementoAttribute);
            }
        }

        gameMemento.setMemento(mementoFields);
        return gameMemento;
    }

    @Override
    public void restore(Memento memento) throws Exception {

        GameMemento gameMemento = (GameMemento) memento;
        HashMap<String, Object> mementoFields = gameMemento.getMemento();
        Class<?> objectClass = requireNonNull(this).getClass();

        for (Field field : objectClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(MementoField.class)) {
                String annotationValue = field.getAnnotation(MementoField.class).value();
                Object mementoField = mementoFields.get(annotationValue);
                field.set(this, mementoField);
            }
        }
    }
}
