/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.savioaraujo.vuttr.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evaldosavio
 */
public class ReflectionUtils {

    /**
     * <p> Obtém o tipo genérico passado na declaração da classe filha. </p>
     *
     * @param type
     * @return
     */
    public static Class getGenericClass(Type type) {
        if (type instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else if (type instanceof Class) {
            return getGenericClass(((Class) type).getGenericSuperclass());
        } else {
            return null;
        }
    }

    /**
     * <p> Obtém os tipos genéricos passado na declaração da classe filha. </p>
     *
     * @param type
     * @return
     */
    public static Type[] getGenericsClass(Type type) {
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments();
        } else if (type instanceof Class) {
            return getGenericsClass(((Class) type).getGenericSuperclass());
        } else {
            return null;
        }
    }

    public static List<Field> getAllFields(Class klass) {
        List<Field> fields = new ArrayList();
        while (!klass.equals(Object.class)) {
            fields.addAll(Arrays.asList(klass.getDeclaredFields()));
            klass = klass.getSuperclass();
        }
        return fields;
    }

    public static boolean contaisField(Object object, String fieldName) {
        return getAllFieldsInMap(object.getClass()).containsKey(fieldName);
    }

    public static Map<String, Field> getAllFieldsInMap(Class klass) {
        Map<String, Field> fieldMap = new HashMap<String, Field>();
        while (!klass.equals(Object.class)) {
            for (Field field : klass.getDeclaredFields()) {
                fieldMap.put(field.getName(), field);
            }
            klass = klass.getSuperclass();
        }

        return fieldMap;
    }

    private static void accessibleField(Field field, String fieldName, Class klass) {
        if (field == null) {
            String error = "Field " + fieldName + " not found in " + klass.getSimpleName();
            Logger.getLogger(ReflectionUtils.class.getName()).log(Level.SEVERE, null, new Exception(error));
            throw new IllegalArgumentException(error);
        } else {
            field.setAccessible(true);
        }
    }

    public static Object getFieldValue(Object object, String field) {
        try {
            if (object != null) {
                Map<String, Field> klassFields = ReflectionUtils.getAllFieldsInMap(object.getClass());

                if (field.contains(".")) {
                    String[] newFields = field.split("\\.");
                    Field f = klassFields.get(newFields[0]);
                    accessibleField(f, newFields[0], object.getClass());
                    Object value;
                    value = f.get(object);
                    if (value != null) {
                        String fieldName = null;
                        for (int i = 1; i < newFields.length; i++) {
                            fieldName = newFields[i];
                            String[] split = null;
                            if (fieldName.contains("=>")) {
                                split = fieldName.split("=>");
                            }
                            String fieldName2 = split == null ? fieldName : split[0];
                            if (value == null) {
                                return null;
                            }
                            f = ReflectionUtils.getAllFieldsInMap(value.getClass()).get(fieldName);
                            accessibleField(f, fieldName2, value.getClass());
                            value = f.get(value);
                            fieldName = (split == null ? fieldName : split[1]);
                        }
                    }
                    return value;

                }

                Field klassField = klassFields.get(field);
                accessibleField(klassField, field, object.getClass());
                Object value = klassField.get(object);
                return value;
            }
            return null;
        } catch (Exception ex) {
            Logger.getLogger(ReflectionUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}
