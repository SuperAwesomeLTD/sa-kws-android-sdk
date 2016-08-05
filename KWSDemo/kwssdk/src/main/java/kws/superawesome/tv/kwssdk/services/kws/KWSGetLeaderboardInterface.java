package kws.superawesome.tv.kwssdk.services.kws;

import java.util.List;

import kws.superawesome.tv.kwssdk.models.leaderboard.KWSLeader;
import kws.superawesome.tv.kwssdk.services.KWSServiceResponseInterface;

/**
 * Created by gabriel.coman on 03/08/16.
 */
public interface KWSGetLeaderboardInterface extends KWSServiceResponseInterface {
    void gotLeaderboard(List<KWSLeader> leaderboard);
}
