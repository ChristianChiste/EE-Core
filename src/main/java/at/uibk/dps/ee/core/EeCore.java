package at.uibk.dps.ee.core;

import java.util.Set;

import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.ExecutionData.ResourceType;
import at.uibk.dps.ee.core.enactable.EnactableRoot;
import at.uibk.dps.ee.core.enactable.EnactmentStateListener;
import at.uibk.dps.ee.core.exception.FailureException;
import at.uibk.dps.ee.core.exception.StopException;

/**
 * Core class performing the enactment.
 * 
 * @author Fedor Smirnov
 *
 */
public class EeCore {

  protected final InputDataProvider inputDataProvider;
  protected final OutputDataHandler outputDataHandler;
  protected final EnactableProvider enactableProvider;

  protected final Set<EnactmentStateListener> stateListeners;

  /**
   * Default constructor (also the one used by Guice)
   * 
   * @param inputDataProvider provider for the input data
   * @param outputDataHandler class handling the data obtained as the result of
   *        the WF execution
   * @param enactableProvider provider of the WF description
   * @param stateListeners classes which react to changes of the enactment state
   */
  public EeCore(final InputDataProvider inputDataProvider,
      final OutputDataHandler outputDataHandler, final EnactableProvider enactableProvider,
      final Set<EnactmentStateListener> stateListeners) {
    this.inputDataProvider = inputDataProvider;
    this.outputDataHandler = outputDataHandler;
    this.enactableProvider = enactableProvider;
    this.stateListeners = stateListeners; 
  }

  /**
   * Obtains the root enactable from the provider, inits it with the input data,
   * and triggers the execution. It is expected that the root enactable cannot
   * throw {@link StopException}.
   * 
   * @throws FailureException
   */
  public void enactWorkflow() throws FailureException {
    final EnactableRoot enactableRoot = enactableProvider.getEnactableApplication();
    final JsonObject inputData = inputDataProvider.getInputData();
    enactableRoot.setInput(inputData);
    for (final EnactmentStateListener stateListener : stateListeners) {
      stateListener.enactmentStarted();
    }
    ExecutionData.resourceType.put("workflow",ResourceType.Local);
    ExecutionData.resourceRegion.put("workflow", "Local");
    ExecutionData.startTimes.put("workflow", System.nanoTime());
    try {
      enactableRoot.play();     
      final JsonObject outputData = enactableRoot.getResult();
      ExecutionData.endTimes.put("workflow", System.nanoTime());
      outputDataHandler.handleOutputData(outputData);
    } catch (StopException stopException) {
      ExecutionData.endTimes.put("workflow", -1L);
      outputDataHandler.handleOutputData(new JsonObject());
      throw new FailureException(stopException);
    }
    finally {
      ExecutionData.startTimes.clear();
      ExecutionData.endTimes.clear();
      ExecutionData.resourceType.clear();
      for (final EnactmentStateListener stateListener : stateListeners) {
        stateListener.enactmentTerminated();
      }
    }
  }
}
