package com.pusulait.airsqreen.annotation.trim;

import javax.persistence.PostLoad;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by benan on 8/17/2015.
 */
public class TrimListener {

    private final Map<Class<?>, Set<Field>> trimProperties =
        new HashMap<Class<?>, Set<Field>>();

    @PostLoad
    public void repairAfterLoad(final Object entity) throws Exception {
        for (final Field fieldToTrim : getTrimProperties(entity.getClass())) {
            final String propertyValue = (String) fieldToTrim.get(entity);
            if (propertyValue != null)
                fieldToTrim.set(entity, propertyValue.trim());
        }
    }

    private Set<Field> getTrimProperties(Class<?> entityClass) throws Exception {
        if (Object.class.equals(entityClass))
            return Collections.emptySet();
        Set<Field> propertiesToTrim = trimProperties.get(entityClass);
        if (propertiesToTrim == null) {
            propertiesToTrim = new HashSet<Field>();
            for (final Field field : entityClass.getDeclaredFields()) {
                if (field.getType().equals(String.class)
                    && field.getAnnotation(Trim.class) != null) {
                    field.setAccessible(true);
                    propertiesToTrim.add(field);
                }
            }
            trimProperties.put(entityClass, propertiesToTrim);
        }
        return propertiesToTrim;
    }

}
