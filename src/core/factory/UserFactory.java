package core.factory;

import models.users.User;

import java.io.Serializable;
import java.util.Map;

/**
 * UserFactory — abstract Factory.
 *
 * Subclasses know how to construct a specific User subtype from a
 * generic key/value Map. The keys each subclass expects are documented
 * in their createUser() Javadoc.
 */
public abstract class UserFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Build a concrete User from a property map.
     */
    public abstract User createUser(Map<String, Object> data);

    // --- helpers ---

    protected static String str(Map<String, Object> data, String key) {
        Object v = data.get(key);
        return (v == null) ? null : v.toString();
    }

    protected static double dbl(Map<String, Object> data, String key, double fallback) {
        Object v = data.get(key);
        if (v instanceof Number) return ((Number) v).doubleValue();
        if (v instanceof String) {
            try { return Double.parseDouble((String) v); }
            catch (NumberFormatException e) { return fallback; }
        }
        return fallback;
    }

    protected static int integer(Map<String, Object> data, String key, int fallback) {
        Object v = data.get(key);
        if (v instanceof Number) return ((Number) v).intValue();
        if (v instanceof String) {
            try { return Integer.parseInt((String) v); }
            catch (NumberFormatException e) { return fallback; }
        }
        return fallback;
    }
}