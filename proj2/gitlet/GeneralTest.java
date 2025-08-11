package gitlet;

import org.junit.Test;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static gitlet.Utils.*;

public class GeneralTest {

    @Test
    public void timeStampTest() {
        System.out.println(new Date(0));
        System.out.println(new Date(System.currentTimeMillis()));
    }

    @Test
    public void writeObj() {
        List<Object> list = new ArrayList<>();
        LinkedList<Object> linkedList = new LinkedList<>();
        linkedList.addLast(list);
        File file = new File(System.getProperty("user.dir"));
        file = join(file, "test");
        writeObject(file, linkedList);
    }

    @Test
    public void HEADFileTest() {
        Repository.readConfig();
        System.out.println(Repository.config.HEAD);
        Commit headcommit = readObject(join(Repository.OBJ_DIR, Repository.config.HEAD), Commit.class);

    }

    @Test
    public void checkFileTest() {
        File objs = Repository.STAGING_DIR;
        List<String> list= plainFilenamesIn(objs);
        System.out.println(list.size());
        for (String s: list) {
            System.out.println(s);
        }
    }
}