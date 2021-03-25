package at.uibk.dps.ee.core;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

/**
 * The {@link OutputDataHandler} processes the workflow output data following
 * the EE configuration.
 * 
 * @author Fedor Smirnov
 */
public interface OutputDataHandler {

  /**
   * Handles the output data produced as the result of the execution.
   * 
   * @param outputData
   * @param executionData 
   */
  void handleOutputData(JsonObject outputData, Map<String, Long> executionStartTimes,
      Map<String, Long> executionEndTimes);
}
