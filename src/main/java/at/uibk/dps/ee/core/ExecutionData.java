package at.uibk.dps.ee.core;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

/**
 * Class used to store information about the execution.
 * 
 * @author Christian Chisté
 *
 */
public final class ExecutionData {

  public static final MultiValuedMap<String,Long> startTimes = new ArrayListValuedHashMap<String,Long>();
  public static final MultiValuedMap<String,Long> endTimes = new ArrayListValuedHashMap<String,Long>();
  public static final MultiValuedMap<String,ResourceType> resourceType = new ArrayListValuedHashMap<String,ResourceType>();
  public static final MultiValuedMap<String,String> resourceRegion = new ArrayListValuedHashMap<String,String>();
  public static double failRate;
  public static String schedulingType;
  public static String workflowName;

  private ExecutionData() {}
  
  public enum ResourceType{
    Amazon,
    IBM,
    Local
  }
}
