package at.uibk.dps.ee.core.exception;

/**
 * The {@link FailureException} is thrown in cases where the execution of the
 * workflow cannot be continued.
 * 
 * @author Fedor Smirnov
 *
 */
public class FailureException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructed with the caught exception which causes the enactment failure.
   * 
   * @param caughtException the cause for the enactment failure
   */
  public FailureException(final StopException caughtException) {
    super(caughtException.getStopMessage(), caughtException);
  }
}
