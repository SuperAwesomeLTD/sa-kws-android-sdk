package kws.superawesome.tv.kwssdk.services.kws.score;

import java.util.List;

import kws.superawesome.tv.kwssdk.models.leaderboard.KWSLeader;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 03/08/16.
 */
public interface KWSChildrenGetLeaderboardInterface extends KWSServiceResponseInterface {
    void didGetLeaderboard (List<KWSLeader> leaderboard);
}
