package gitlet;



import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date; // TODO: You'll likely use this in this class

import java.util.TreeMap;

import static gitlet.Utils.serialize;
import static gitlet.Utils.sha1;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    /** The parent commit of this Commit. */
    private String parent;
    /** The mergedparent commit of this Commit. */
    private String mergedParent;
    /** The time of this Commit being created. */
    private Date timestamp;
    /** The directory of the files*/
    private TreeMap<String, String> files;

    public Commit(String message, String parent, String mergedParent, Date timestamp) {
        this.message = message;
        this.parent = parent;
        this.mergedParent = mergedParent;
        this.timestamp = timestamp;
        this.files = null;
    }

    public Commit(Commit oldCommit, String message, String mergedParent) {
        this.message = message;
        this.parent = getHash(oldCommit);
        this.mergedParent = mergedParent;
        this.timestamp = new Date(System.currentTimeMillis());
        this.files = new TreeMap<>();
    }



    public TreeMap<String, String> getFiles() {
        return files;
    }

    private static String getHash(Serializable obj) {
        return sha1(serialize(obj));
    }




}
