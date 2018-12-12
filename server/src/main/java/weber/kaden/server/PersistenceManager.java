package weber.kaden.server;

import java.util.List;

import weber.kaden.common.command.Command;
import weber.kaden.common.command.CommandData.CommandData;
import weber.kaden.common.command.CommandFactory;
import weber.kaden.common.injectedInterfaces.persistence.DaoFactory;
import weber.kaden.common.model.Model;

public class PersistenceManager {
    private static PersistenceManager persistenceManager;
    private int deltaValue;
    private int deltaCount;
    private DaoFactory daoFactory;

    public static PersistenceManager getInstance() {
        if (persistenceManager == null) {
            persistenceManager = new PersistenceManager();
        }
        return persistenceManager;
    }

    public int getDeltaValue() {
        return deltaValue;
    }

    public void setDeltaValue(int deltaValue) {
        this.deltaValue = deltaValue;
    }

    public DaoFactory getDaoFactory() {
        return daoFactory;
    }

    public void setDaoFactory(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void update(CommandData data) {
        this.deltaCount++;
        this.daoFactory.addCommandData(data);
        if (this.deltaCount == this.deltaValue) {
            this.saveToDb();
        }
    }

    public void saveToDb() {
        this.deltaCount = 0;
        this.daoFactory.clearCommandDeltas();
        this.daoFactory.saveUsers(Model.getInstance().getPlayers());
        this.daoFactory.saveGames(Model.getInstance().getGames());
    }

    public void loadFromDB() {
        Model.getInstance().setPlayers(this.daoFactory.getUsers());
        Model.getInstance().setGames(this.daoFactory.getGames());
        List<CommandData> data = this.daoFactory.getCommands();
        for (int i = 0; i < data.size(); i++) {
            try {
                Command command = CommandFactory.getInstance().getCommand(data.get(i));
                command.execute();
            } catch (Exception e){
                e.printStackTrace();
                System.out.print(data.get(i).getType().toString());
            }
        }
        this.daoFactory.clearCommandDeltas();
    }

    public void wipeDB(){
        this.daoFactory.clear();
    }
}
