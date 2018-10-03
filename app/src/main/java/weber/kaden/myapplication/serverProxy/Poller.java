package weber.kaden.myapplication.serverProxy;

public class Poller {
    private ClientCommunicator ccom;
    private int waitTime;

    private Thread runningThread;

    public Poller(int miliseconds) {
        waitTime = miliseconds;
        ccom = ClientCommunicator.getInstance();
    }

    public void startPolling() {

    }

    public void stopPolling() {

    }

    private class CommandsGetter extends Thread {
        @Override
        public void run() {

        }
    }
}
