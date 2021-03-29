package at.uibk.dps.ee.core;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

public final class ExecutionData {

  public static final MultiValuedMap<String,Long> startTimes = new ArrayListValuedHashMap<String,Long>();
  public static final MultiValuedMap<String,Long> endTimes = new ArrayListValuedHashMap<String,Long>();
  public static final MultiValuedMap<String,ResourceType> resourceType = new ArrayListValuedHashMap<String,ResourceType>();
  public static double failRate;
  public static SchedulingType schedulingType;

  private ExecutionData() {}
  
  public enum ResourceType{
    Amazon,
    IBM,
    Local
  }
  
  public enum SchedulingType{
    Dynamic,
    StaticSingle,
    StaticAll
  }
}
