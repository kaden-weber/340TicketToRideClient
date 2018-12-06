package weber.kaden.common.command;


import weber.kaden.common.results.Results;

public interface Command {

    Results execute();

    boolean hasID();
}
