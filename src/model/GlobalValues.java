package model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alex on 8/13/2017.
 */
public class GlobalValues {

    private final Hashtable<GlobalValueType, SimpleObjectProperty<Object>> values = new Hashtable<>();

    public GlobalValues() {
        for (GlobalValueType key : GlobalValueType.values()) {
            values.put(key, new SimpleObjectProperty<>(key.getDefaultVal()));
        }
    }

    public synchronized void setValue(GlobalValueType key, Object val) {
        if (key.getClazz().isInstance(val)) {
            values.get(key).setValue(val);
        } else {
            Logger.getGlobal().log(Level.WARNING, "Attempting setting value of incorrect type " + key + " with value " + val);
        }
    }

    public Object getValue(GlobalValueType key, Object defaultValue) {
        return (values.get(key).getValue() != null ? values.get(key).getValue() : defaultValue);
    }

    public void addValueChangedListener(GlobalValueType key, ChangeListener<Object> changeListener) {
        values.get(key).addListener(changeListener);
    }

}
