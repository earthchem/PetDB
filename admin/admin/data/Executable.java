package admin.data;

import java.util.*;

public interface Executable {

public boolean runAction();
public Vector getColumns();
public void setValue(int i, String v);
public boolean  actionPerformed();
public String getMessage();
}
