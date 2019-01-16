package com.example.domain.executor;

import java.util.concurrent.Executor;
import com.example.domain.interactor.UseCase;

/**
 * Executor implementation can be based on different framework or technique of asynchronous
 * execution, but every implementation will execute the {@link UseCase} out of the UI thread
 */
public interface ThreadExecutor extends Executor {
}
