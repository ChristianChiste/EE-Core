package at.uibk.dps.ee.core;

import java.util.ArrayList;
import java.util.List;

public final class ExecutionData {
  
 public static final List<Integer> data = new ArrayList<Integer>();
  
  private ExecutionData() {}

  public List<Integer> getData() {
    return data;
  }

  public void addData(int data2) {
    data.add(data2);
  }

  
}
