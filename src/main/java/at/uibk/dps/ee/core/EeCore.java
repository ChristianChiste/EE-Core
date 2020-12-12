package at.uibk.dps.ee.core;

import java.util.Set;

import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.Enactable;
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
	protected final WorkflowProvider workflowProvider;
	
	protected final Set<EnactmentStateListener> stateListeners;

	/**
	 * Default constructor (also the one used by Guice)
	 * 
	 * @param inputDataProvider provider for the input data
	 * @param outputDataHandler class handling the data obtained as the result of the WF execution
	 * @param workflowProvider provider of the WF description
	 * @param stateListeners classes which react to changes of the enactment state
	 */
	public EeCore(final InputDataProvider inputDataProvider, final OutputDataHandler outputDataHandler,
			final WorkflowProvider workflowProvider, final Set<EnactmentStateListener> stateListeners) {
		this.inputDataProvider = inputDataProvider;
		this.outputDataHandler = outputDataHandler;
		this.workflowProvider = workflowProvider;
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
		final Enactable enactableRoot = workflowProvider.getEnactableApplication();
		final JsonObject inputData = inputDataProvider.getInputData();
		enactableRoot.init(inputData);
		for (final EnactmentStateListener stateListener : stateListeners) {
			stateListener.enactmentStarted();
		}
		try {
			final JsonObject outputData = enactableRoot.play();
			outputDataHandler.handleOutputData(outputData);
		} catch (StopException stopException) {
			// The root should never throw exceptions.
			throw new FailureException(stopException);
		}
		for (final EnactmentStateListener stateListener : stateListeners) {
			stateListener.enactmentTerminated();
		}
	}
}