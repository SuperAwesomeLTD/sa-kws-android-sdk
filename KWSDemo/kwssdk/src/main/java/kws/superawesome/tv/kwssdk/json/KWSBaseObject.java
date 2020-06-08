/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package kws.superawesome.tv.kwssdk.json;

import org.json.JSONObject;

/**
 * This acts as a type of base object for all models that might be used later on by the SDK.
 * It already contains an implementation for the basic methods that would need to be
 * implemented by a "Serializable" object
 */
public class KWSBaseObject implements KWSJsonSerializable {

    /**
     * Basic constructor that does nothing
     */
    public KWSBaseObject() {
        // default
    }

    /**
     * Base standard constructor that has a string as a parameter from which to try to
     * make out all other fields in the object.
     * According to the KWSJsonSerializable interface, the actual fields of the object are
     * going to be "translated" from JSON to a model in the "readFromJson" method.
     *
     * @param json  a hopefully valid JSON string
     */
    public KWSBaseObject(String json) {
        JSONObject jsonObject = KWSJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    /**
     * Base standard constructor that has a JSONObject as a parameter from which to try to
     * make out all other fields in the object.
     * According to the KWSJsonSerializable interface, the actual fields of the object are
     * going to be "translated" from JSON to a model in the "readFromJson" method.
     *
     * @param jsonObject a hopefully valid JSON object
     */
    public KWSBaseObject(JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    /**
     * Overridden KWSJsonSerializable method that will need to describe how a certain JSON model
     * corresponds to a model
     *
     * @param json a valid JSON string
     */
    @Override
    public void readFromJson(JSONObject json) {
        // empty implementation
    }

    /**
     * Overridden KWSJsonSerializable method that will need to describe how a certain model
     * corresponds to a JSON model
     *
     * @return an empty JSONObject
     */
    @Override
    public JSONObject writeToJson() {
        return new JSONObject();
    }

    /**
     * Overridden KWSJsonSerializable method that will need to describe the validity of what's
     * been read from JSON
     *
     * @return false, by default
     */
    @Override
    public boolean isValid() {
        return false;
    }
}
