package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;
import static gitlet.Utils.join;


/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Tau
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The config file*/
    public static final File CONFIG = join(GITLET_DIR, ".config");
    public static final File OBJ_DIR = join(GITLET_DIR, "objs");
    public static final File STAGING_DIR = join(GITLET_DIR, "staging");
    public static final File REMOVAL_DIR = join(GITLET_DIR, "removal");

    private static Config config = new Config();

    public static void saveConfig() {
        writeObject(CONFIG, config);
    }

    public static void readConfig() {
        config = readObject(CONFIG, config.getClass());
    }

    private static String getHash(Serializable obj) {
        return sha1(serialize(obj));
    }

    public static void saveCommit(Commit commit) {
        assert (commit != null);
        String hash = getHash(commit);
        File commitFile = join(OBJ_DIR, hash);
        assert (!commitFile.exists());
        writeObject(commitFile, commit);
    }

    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        OBJ_DIR.mkdirs();
        STAGING_DIR.mkdir();
        REMOVAL_DIR.mkdir();
        Commit init = new Commit("initial commit", null, null, new Date(0));

        String hashOfCommit = getHash(init);
        config.HEAD = hashOfCommit;
        config.commits.addLast(hashOfCommit);
        config.branches.put("master", hashOfCommit);

        saveCommit(init);
        saveConfig();
    }

    public static void add(String stagingFile) {
        // read the config into program.
        readConfig();

        // exit if the file we want to stage doesn't exist.
        File file = join(CWD, stagingFile);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        // get the hash value of the file we want to add.
        String stagingFileHash = sha1(readContents(file));

        // check if there is a file that is identical to the file we want to stage.
        // or is there an identical file at the staging folder.
        Commit headCommit = readObject(join(OBJ_DIR, config.HEAD), Commit.class);
        File cashe = join(STAGING_DIR, stagingFile);
        Map<String, String> map = headCommit.getFiles();

        if (map != null) {
            String commitFileHash = map.get(stagingFile);
            if (commitFileHash != null) {
                if (cashe.exists() && commitFileHash.equals(stagingFileHash)) {
                    cashe.delete();
                    return;
                }
                if (commitFileHash.equals(stagingFileHash)) {
                    return;
                }
            } else {
                writeContents(cashe, readContents(file));
                return;
            }
        }
        // stage the file to the folder
        writeContents(cashe, readContents(file));
    }


    public static void remove(String removedFile) {
        // read the config into program.
        readConfig();

        // calculate the hash value of the file.
        File inWorking = join(CWD, removedFile);
        String hash = sha1(readContents(inWorking));

        //using the hash value to create File object verify if it is added or tracked.
        File inStaging = join(STAGING_DIR, removedFile);
        File inObj = join(OBJ_DIR, hash);
        File removal = join(REMOVAL_DIR, removedFile);

        // if it is added. then we manipulate as follows.
        if (inStaging.exists()) {
            inStaging.delete(); // delete the file from staging folder.
            return;
        }

        // if it is tracked. we do as follows.
        if (inObj.exists()) {

            writeContents(removal, readContents(inWorking)); // make it untracked. (put it in removal folder)
            inWorking.delete(); // if the file is tracked, first we delete it from CWD.

            return;
        }

        if ((!inStaging.exists()) && (!inObj.exists())) {
            System.out.println("No reason to remove the file.");
            return;
        }

    }

    public static void commit(String message) {

        // clone the content of old commit.
        readConfig();
        Commit oldCommit = readObject(join(OBJ_DIR, config.HEAD), Commit.class);
        Commit newCommit = new Commit(oldCommit, message, null);

        //check if there is any changes.
        if (!newCommit.isThereChanges()) {
            System.out.println("No changes added to the commit.");
            return;
        }

        newCommit.updateFiles();  // update the table of commit.

        // save staging file to obj and delete it from staging folder.
        newCommit.saveStagingFiles();
        newCommit.cleanStagingFile();

        // save config
        String newCommitHash = getHash(newCommit);
        config.HEAD = newCommitHash;
        config.commits.addLast(newCommitHash);
        saveConfig();

        // save commit
        saveCommit(newCommit);
    }

    public static void log() {
        readConfig();

        File HEADCommit = join(OBJ_DIR, config.HEAD);
        Commit HEAD = readObject(HEADCommit, Commit.class);

        while (HEAD != null) {
            System.out.println("===");
            System.out.println("commit " + getHash(HEAD));
            System.out.println("Date: " + HEAD.getTimestamp());
            System.out.println("Message " + HEAD.getMessage());
            System.out.println("");
            if (HEAD.getParent() != null) {
                File parentCommit = join(OBJ_DIR, HEAD.getParent());
                HEAD = readObject(parentCommit, Commit.class);
            } else {
                return;
            }
        }
    }

    public static void checkoutHEADCommit(String fileName) {
        readConfig();
        checkoutCommit(config.HEAD, fileName);
    }

    public static void checkoutBranch(String branchName, String fileName) {
        readConfig();
    }

    // unchecked yet.
    public static void checkoutCommit(String commitHash, String fileName) {
        readConfig();
        for (String commit: config.commits) {
            if (commit.equals(commitHash)) {
                File commitFile = join(OBJ_DIR, commit);
                Commit commitFinded = readObject(commitFile, Commit.class);
                File fileOverwritten = join(CWD, fileName);
                String commitVersionHash = commitFinded.getFiles().get(fileName);
                if (commitVersionHash == null) {
                    System.out.println("File does not exist in that commit.");
                    return;
                }
                File commitVersion = join(OBJ_DIR, commitVersionHash);
                writeContents(fileOverwritten, readContents(commitVersion));
                return;
            }
        }
        System.out.println("No commit with that id exists.");
    }

}
