package kws.superawesome.tv.kwssdk.services.kws;

import kws.superawesome.tv.kwssdk.models.user.KWSScore;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 24/08/16.
 */
public interface KWSGetScoreInterface extends KWSServiceResponseInterface {
    void gotScore(KWSScore score);
}
