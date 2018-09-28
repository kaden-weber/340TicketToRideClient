package weber.kaden.myapplication.serverProxy;

public class Poller {
    private ClientCommunicator ccom;
    private int waitTime;

    public Poller(int miliseconds) {
        waitTime = miliseconds;
        ccom = ClientCommunicator.getInstance();
    }

    public void startPolling() {
        //TODO: use the commandgetter class in a thread to periodically call and return stuff
    }

    public void stopPolling() {

    }

    private class CommandsGetter implements Runnable {
        @Override
        public void run() {

        }
    }
}
