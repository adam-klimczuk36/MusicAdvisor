package advisor.model;

import org.apache.commons.cli.*;

import java.util.stream.Collectors;

public class Utils {

    public static CommandLine getCliArguments(String[] args) {

        Options options = new Options();

        Option access = new Option("access", true, "authorization server path");
        access.setRequired(false);
        options.addOption(access);

        Option resource = new Option("resource", true, "API server path");
        resource.setRequired(false);
        options.addOption(resource);

        Option page = new Option("page", true, "a number of entries that should be shown on a page");
        page.setRequired(false);
        options.addOption(page);

        Option secret = new Option("secret", true, "a secret key needed for app to work");
        secret.setRequired(true);
        options.addOption(secret);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;//not a good practice, it serves it purpose

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        return cmd;
    }
}
