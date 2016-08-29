package admin.dbAccess;

import java.util.*;

public class Record {

      Vector record;

      public Record() {
             record = new Vector();
      }

      public void addValue(String f) {
              record.add(f);
      }

      public String valueAt(int index) {
             return (String)record.elementAt(index);
      }
      
      public int size()
      {
    	  return record.size();
      }

}
