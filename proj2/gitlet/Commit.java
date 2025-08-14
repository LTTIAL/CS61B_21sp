package gitlet;



import java.io.File;
import java.io.Serializable;
import java.util.Date;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static gitlet.Utils.*;
import static gitlet.Utils.join;

/** Represents a gitlet commit object.
 *
 *  does at a high level.
 *
 *  @author Tau
 */
public class Commit implements Serializable {
    /**
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
        // if the old commit has a non-null files copy it.
        Map<String, String> map = oldCommit.getFiles();
        this.files = new TreeMap<>();
        if (map != null) {
            this.files.putAll(map);
        }
    }

    public String getParent() {
        return parent;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public TreeMap<String, String> getFiles() {
        return files;
    }

    private static String getHash(Serializable obj) {
        return sha1(serialize(obj));
    }

    public boolean isThereChanges() {
        List<String> staging = plainFilenamesIn(Repository.STAGING_DIR);
        List<String> removal = plainFilenamesIn(Repository.REMOVAL_DIR);
        if (staging.size() != 0 || removal.size() != 0) {
            return true;
        }
        return false;
    }

    //update commit depend on the file in staging and removal folder.
    public void updateFiles() {
        List<String> staging = plainFilenamesIn(Repository.STAGING_DIR);
        List<String> removal = plainFilenamesIn(Repository.REMOVAL_DIR);

        for (String s: staging) {
            File file = join(Repository.STAGING_DIR, s);
            String fileHash = sha1(readContents(file));
            files.put(s, fileHash);
        }
        for (String s: removal) {
            files.remove(s);
        }
    }

    // copy the file in staging folder to obj.
    public void saveStagingFiles() {
        List<String> staging = plainFilenamesIn(Repository.STAGING_DIR);
        for (String s: staging) {
            File fileStaging = join(Repository.STAGING_DIR, s);
            String fileHash = sha1(readContents(fileStaging));
            File fileSaved = join(Repository.OBJ_DIR, fileHash);
            writeContents(fileSaved, readContents(fileStaging));
        }
    }

    // clean the staging folder and the removal folder.
    public void cleanStagingFile() {
        List<String> staging = plainFilenamesIn(Repository.STAGING_DIR);
        List<String> removal = plainFilenamesIn(Repository.REMOVAL_DIR);
        for (String s: staging) {
            File fileStaging = join(Repository.STAGING_DIR, s);
            fileStaging.delete();
        }
        for (String s: removal) {
            File fileRemoval = join(Repository.REMOVAL_DIR, s);
            fileRemoval.delete();
        }
    }


}
