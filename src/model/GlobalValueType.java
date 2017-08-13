package model;

/**
 * Created by Alex on 8/13/2017.
 */
public enum GlobalValueType {

    SKIP_SONG(Boolean.class, false), SONG_TO_PLAY(Song.class, null);

    private Class clazz;
    private Object defaultVal;

    GlobalValueType(Class clazz, Object defaultVal) {
        this.clazz = clazz;
        this.defaultVal = defaultVal;
    }

    public Class getClazz() {
        return clazz;
    }

    public Object getDefaultVal() {
        return defaultVal;
    }
}
