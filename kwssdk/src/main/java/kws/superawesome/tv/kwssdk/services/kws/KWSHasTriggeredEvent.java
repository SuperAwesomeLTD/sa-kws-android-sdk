package kws.superawesome.tv.kwssdk.services.kws;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.models.event.KWSEventStatus;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 24/08/16.
 */
public class KWSHasTriggeredEvent extends KWSService {

    private int eventId = 0;
    private KWSHasTriggeredEventInterface listener = null;

    public KWSHasTriggeredEvent () {
        listener = new KWSHasTriggeredEventInterface() { @Override public void hasTriggered(Boolean triggered) {} };
    }

    @Override
    public String getEndpoint() {
        return "v1/users/" + super.loggedUser.metadata.userId + "/has-triggered-event";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
    }

    @Override
    public JSONObject getBody() {
        return SAJsonParser.newObject(new Object[] {
            "eventId", eventId
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.hasTriggered(false);
        } else {
            if (status == 200 && payload != null) {
                Log.d("SuperAwesome", "Payload ==> " + payload);
                JSONObject json = SAJsonParser.newObject(payload);
                KWSEventStatus eventStatus = new KWSEventStatus(json);
                listener.hasTriggered(eventStatus.hasTriggeredEvent);
            } else {
                listener.hasTriggered(false);
            }
        }
    }

    public void execute(Context context, int eventId, KWSServiceResponseInterface listener) {
        this.eventId = eventId;
        this.listener = listener != null ? (KWSHasTriggeredEventInterface) listener : this.listener;
        super.execute(context, this.listener);
    }
}
