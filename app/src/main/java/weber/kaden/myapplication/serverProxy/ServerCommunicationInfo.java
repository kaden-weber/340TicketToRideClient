package weber.kaden.myapplication.serverProxy;

public class ServerCommunicationInfo {
	private static String serverIPAddress = "18.219.136.215";
	private static String serverPort = "8080";

	public static String getServerIPAddress() {
		return serverIPAddress;
	}

	public static void setServerIPAddress(String serverIPAddress) {
		ServerCommunicationInfo.serverIPAddress = serverIPAddress;
	}

	public static String getServerPort() {
		return serverPort;
	}

	public static void setServerPort(String serverPort) {
		ServerCommunicationInfo.serverPort = serverPort;
	}
}