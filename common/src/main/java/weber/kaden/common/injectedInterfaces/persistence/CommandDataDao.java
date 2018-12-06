package weber.kaden.common.injectedInterfaces.persistence;

import java.util.List;

import weber.kaden.common.command.CommandData.CommandData;

public interface CommandDataDao {
    boolean save(List<CommandData> data);
    boolean clear();
    boolean add(CommandData data);
    List<CommandData> getCommands();
}
