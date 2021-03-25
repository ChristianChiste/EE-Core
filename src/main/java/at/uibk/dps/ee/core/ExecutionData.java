package at.uibk.dps.ee.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ExecutionData {

  public static final Map<String,Long> startTimes = new HashMap<String,Long>();
  public static final Map<String,Long> endTimes = new HashMap<String,Long>();

  private ExecutionData() {}

}
