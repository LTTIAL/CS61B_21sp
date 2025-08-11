package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new GitletException();
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                if (args.length < 2) {
                    throw new GitletException();
                }
                String stagingFile = args[1];
                Repository.add(stagingFile);
                break;
            case "commit":
                if (args.length < 2) {
                    System.out.println("Please enter a commit message.");
                    return;
                }
                String message = args[1];
                Repository.commit(message);
                break;
        }
    }
}
