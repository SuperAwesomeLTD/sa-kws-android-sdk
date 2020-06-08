/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package kws.superawesome.tv.kwssdk.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class that groups together a large number of methods needed to do proper JSON parsing.
 * It also tries to respect the null-object-pattern so that no matter what you throw at it it'll
 * return a valid value, not a null
 */
public class KWSJsonParser {

    /**
     * Function that safely puts a value in a Json object
     * @param jsonObject the target Json object
     * @param key        the key under which to put the target object
     * @param object     the actual target object
     */
    public static void put(JSONObject jsonObject, String key, Object object) {

        // checks
        if (jsonObject == null || key == null) return;

        // if current object is null the just add a standard NULL to the JSON
        if (object == null) {
            try {
                jsonObject.put(key, JSONObject.NULL);
            } catch (JSONException ignored) {
                // do nothing
            }
        }
        // if object isn't NULL and is a subclass of KWSBaseObject
        else if (object instanceof KWSBaseObject) {
            try {
                jsonObject.put(key, ((KWSBaseObject) object).writeToJson());
            } catch (JSONException ignored) {
                // do nothing
            }
        }
        // if it's any other object just add it
        else {
            try {
                jsonObject.put(key, object);
            } catch (JSONException ignored) {
                // do nothing
            }
        }
    }

    /**
     * Create a JSON object more elegantly
     * @param args   an array of key-value pairs given as
     *               "key1", val1, "key2", val2
     * @return       a valid JSONObject (or an empty one)
     */
    public static JSONObject newObject (Object... args) {

        // create a new json Object
        JSONObject jsonObject = new JSONObject();

        // exit immediately
        if (args == null || args.length == 0) return jsonObject;

        // go through it, two at a time
        for (int i = 0; i < args.length; i += 2) {

            Object key = null, val = null;

            try {
                key = args[i];
            } catch (IndexOutOfBoundsException e) {
                // do nothing
            }

            try {
                val = args[i+1];
            } catch (IndexOutOfBoundsException e) {
                // do nothing
            }

            // if all's well, then try to put it
            if (key != null && key instanceof String && val != null) {
                put(jsonObject, (String) key, val);
            }

        }

        // return the json
        return jsonObject;

    }

    /**
     * Init a Json object from a string, w/o the hassle of try/catch
     * @param json  the JSON string
     * @return      a valid JSON object
     */
    public static JSONObject newObject (String json) {
        if (json == null) {
            return new JSONObject();
        } else {
            try {
                return new JSONObject(json);
            } catch (JSONException e) {
                return new JSONObject();
            }
        }
    }

    /**
     * Init a JSON array from a string, w/o the hassle of try/catch
     * @param json  the JSON string
     * @return      a valid JSON array
     */
    public static JSONArray newArray (String json) {
        if (json == null) {
            return new JSONArray();
        } else {
            try {
                return new JSONArray(json);
            } catch (JSONException e) {
                return new JSONArray();
            }
        }
    }

    /**
     * Create a new Json array
     * @param args  an array of arguments
     * @return      a json array object
     */
    public static JSONArray newArray (Object... args) {
        JSONArray jsonArray = new JSONArray();

        // exit immediately
        if (args == null) return jsonArray;

        // go throuh the array and put it's values
        for (Object arg : args) {
            jsonArray.put(arg);
        }

        return jsonArray;
    }

    /**
     * Standard get functions
     * @param jsonObject target json object
     * @param key        the key
     * @return           a valid java Object
     */
    public static Object get(JSONObject jsonObject,  String key) {
        if (jsonObject == null) {
            return null;
        }
        else {
            if (!jsonObject.isNull(key)) {
                try {
                    return jsonObject.get(key);
                } catch (JSONException e) {
                    return null;
                }
            } else return null;
        }
    }

    /**
     * Standard get w/ a default value
     * @param jsonObject target json object
     * @param key        the key
     * @return           a valid java Object
     */
    public static Object get( JSONObject jsonObject, String key, Object def) {
        if (jsonObject == null) {
            return def;
        }
        else {
            if (!jsonObject.isNull(key)) {
                try {
                    return jsonObject.get(key);
                } catch (JSONException e) {
                    return def;
                }
            } else return def;
        }
    }

    /**
     * Get a specific string
     * @param jsonObject target json object
     * @param key        the key
     * @return           a string value from the JSON
     */
    public static String getString(JSONObject jsonObject, String key) {
        Object object = get(jsonObject, key);
        if (object != null && object instanceof String) {
            return (String)object;
        } else {
            return null;
        }
    }

    /**
     * Get a specific integer value
     * @param jsonObject target json object
     * @param key        the key
     * @return           an integer value from the JSON
     */
    public static int getInt(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getInt(key);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Get a specific integer value w/ a default value
     * @param jsonObject target json object
     * @param key        the key
     * @param def        the default integer to return
     * @return           a integer value from the JSON
     */
    public static int getInt(JSONObject jsonObject, String key, int def) {
        try {
            return jsonObject.getInt(key);
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * Get a specific json object value w/ a default value
     * @param jsonObject target json object
     * @param key        the key
     * @return           a json object value from the JSON
     */
    public static JSONObject getJsonObject(JSONObject jsonObject, String key) {
        Object object = get(jsonObject, key);
        if (object != null && object instanceof JSONObject) {
            return (JSONObject) object;
        } else {
            return new JSONObject();
        }
    }
}
