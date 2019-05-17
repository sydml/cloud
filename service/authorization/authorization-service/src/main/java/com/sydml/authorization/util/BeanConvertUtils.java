package com.sydml.authorization.util;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Liuym
 * @date 2019/3/29 0029
 */
public class BeanConvertUtils {
    private BeanConvertUtils() {
    }

    public static Map<String, Object> toMap(Object object) {
        return toMap(object, false);
    }

    public static Map<String, Object> toMapIgnoreNull(Object object) {
        return toMap(object, true);
    }

    private static Map<String, Object> toMap(Object object, Boolean isIgnoreNull) {
        Assert.notNull(object, "object cannot be null");
        List<Field> fields = getFields(object.getClass());
        Map<String, Object> result = new HashMap<>(fields.size());
        fields.stream()
                .map(field -> new Tuple<>(field.getName(), getField(field, object)))
                .filter(tuple -> !isIgnoreNull || tuple._2 != null)
                .forEach(tuple -> result.put(tuple._1, tuple._2));
        return result;
    }

    public static Object getField(Field field, Object object) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Field> getFields(Class clazz) {
        List<Field> result = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        Optional.ofNullable(clazz.getSuperclass()).ifPresent(addSuperclassFields(result));
        return result;
    }

    private static Consumer<Class> addSuperclassFields(List<Field> result) {
        return superClass -> {
            List<Field> superclassFields = getFields(superClass);
            List<String> resultNames = result.stream().map(Field::getName).collect(Collectors.toList());
            List<Field> validSuperclassFields =
                    superclassFields.stream().filter(superclassField -> !resultNames.contains(superclassField.getName())).collect(Collectors.toList());
            result.addAll(validSuperclassFields);
        };
    }
}
