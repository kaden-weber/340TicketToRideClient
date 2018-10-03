package weber.kaden.myapplication.serverProxy;

public class ServerConnectionInfo {
	private static String serverIPAddress = "0.0.0.0";
	private static String serverPort = "8080";

	public static String getServerIPAddress() {
		return serverIPAddress;
	}

	public static void setServerIPAddress(String serverIPAddress) {
		ServerConnectionInfo.serverIPAddress = serverIPAddress;
	}

	public static String getServerPort() {
		return serverPort;
	}

	public static void setServerPort(String serverPort) {
		ServerConnectionInfo.serverPort = serverPort;
	}
}
