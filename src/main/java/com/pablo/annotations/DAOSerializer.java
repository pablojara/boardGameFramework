package com.pablo.annotations;


import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import static java.util.Objects.requireNonNull;

public class DAOSerializer {


    public static void serialize(Object object) throws Exception {

        Class<?> objectClass = requireNonNull(object).getClass();
        Map<String, Object> serializationElements = new HashMap<>();

        for (Field field : objectClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(DAOField.class)) {
                System.out.println(getSerializedKey(field));
                serializationElements.put(getSerializedKey(field), field.get(object));
            }
        }
        toByteCode(serializationElements);
    }

    public static Object deserialize(Object object) throws Exception {
        Class<?> objectClass = requireNonNull(object).getClass();
        List<String> fileNames = new ArrayList<String>();
        for (Field field : objectClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(DAOField.class)) {
                fileNames.add(getSerializedKey(field));

            }
        }

        HashMap<String, Object> readObjects = toObject(fileNames);

        for (Field field : objectClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(DAOField.class)) {
                field.set(object, readObjects.get(getSerializedKey(field)));
            }
        }

        return object;
    }

        public static void toByteCode(Map<String, Object> serializationElements) throws Exception {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        Iterator it = serializationElements.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            try {
                String fileName = Objects.toString(pair.getKey());
                fileOutputStream = new FileOutputStream(fileName);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(pair.getValue());
                objectOutputStream.close();
                System.out.println("Object serialized in file : " + fileName);
            }
            catch (FileNotFoundException e) {
                throw new Exception(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            it.remove();
        }
    }

    public static HashMap<String, Object> toObject(List<String> fileNames) {
        HashMap<String, Object> readObjects = new HashMap<String, Object>();
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        for(String fileName: fileNames) {
            try {
                fileInputStream = new FileInputStream(fileName);
                objectInputStream = new ObjectInputStream(fileInputStream);
                readObjects.put(fileName, objectInputStream.readObject());
                objectInputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return readObjects;
    }

    private static String getSerializedKey(Field field) {
        String annotationValue = field.getAnnotation(DAOField.class).value();

        if (annotationValue.isEmpty()) {
            return field.getName();
        }
        else {
            return annotationValue;
        }
    }
}
