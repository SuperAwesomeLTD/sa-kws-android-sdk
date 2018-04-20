package kws.superawesome.tv.kwssdk;

import java.util.concurrent.Executor;

/**
 * Created by guilherme.mota on 10/01/2018.
 */

public class MockExecutor implements Executor {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
