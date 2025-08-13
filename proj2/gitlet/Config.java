package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Config implements Serializable {
    public String HEAD;
    public LinkedList<String> commits;
    public Map<String, String> branches;

    public Config() {
        HEAD = null;
        commits = new LinkedList<>();
        branches = new HashMap<>();
    }
}
