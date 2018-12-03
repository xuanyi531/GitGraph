import helper.Configuration;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

/**
 * Created by Carol on 2018/11/27.
 */

public class Main {
    public static void main(String[] args){
        ArgumentParser parser = ArgumentParsers.newFor("Main").build()
                .defaultHelp(true)
                .description("Analyse a git project and build visible graph");

        parser.addArgument("-p", "--project")
                .required(true)
                .help("git project root directory");
        parser.addArgument("-f", "--filter")
                .help("optional, type filter switch for source file");
        parser.addArgument("-d", "--database")
                .help("optional, specify database path");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        String project = ns.getString("project");
        Configuration.project = project;

        String filter = ns.getString("filter");
        if (filter == null){
            filter = "";
        }

        String database = ns.getString("database");
        if (database != null){
            Configuration.database = database;
        }
        System.out.println("Starting...");
        new Analyser(project, filter).run();
        System.out.println("Done.");
    }
}
