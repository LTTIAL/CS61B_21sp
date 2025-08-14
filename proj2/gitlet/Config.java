package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Config implements Serializable {
    private String HEAD;
    private LinkedList<String> commits;
    private Map<String, String> branches;

    public Config() {
        HEAD = null;
        commits = new LinkedList<>();
        branches = new HashMap<>();
    }

    public String getHEAD() {
        return HEAD;
    }

    public void setHEAD(String head) {
        HEAD = head;
    }

    public LinkedList<String> getCommits() {
        return commits;
    }

    public void setCommits(LinkedList<String> commits) {
        this.commits = commits;
    }

    public void setBranches(Map<String, String> branches) {
        this.branches = branches;
    }

    public Map<String, String> getBranches() {
        return branches;
    }
}
