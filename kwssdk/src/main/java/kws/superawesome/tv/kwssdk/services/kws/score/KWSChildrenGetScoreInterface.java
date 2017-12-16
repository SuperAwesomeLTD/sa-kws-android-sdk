package kws.superawesome.tv.kwssdk.services.kws.score;

import kws.superawesome.tv.kwssdk.models.user.KWSScore;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 24/08/16.
 */
public interface KWSChildrenGetScoreInterface extends KWSServiceResponseInterface {
    void didGetScore (KWSScore score);
}
