import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import model.Config;
import model.User;
import networking.TeamspeakListener;

public class TeamspeakQuery {
	public void start() {
		ArrayList<User> users = new ArrayList<>();

		TS3Config config = new TS3Config();
		TS3Query query = new TS3Query(config);
		TS3Api api = query.getApi();
		int queryClientId;

		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);

		config = new TS3Config();
		config.setHost(Config.IPADDRESS);
		config.setQueryPort(10011);
		config.setDebugLevel(Level.ALL);

		System.out.println("Connecting...");
		query.connect();

		api.login(Config.QUERYUSER, Config.QUERYPASS);
		api.selectVirtualServerById(1);
		api.setNickname("QueryBot");
		queryClientId = api.whoAmI().getId();

		// Listen to chat in the channel the query is currently in
		// As we never changed the channel, this will be the default channel of
		// the server
		// api.registerEvent(TS3EventType.TEXT_CHANNEL, 0);
		api.registerEvent(TS3EventType.TEXT_PRIVATE); // Won't listen to old
														// chat window, may need
														// to reopen it.
		api.registerAllEvents();
		// Register the event listener
		api.addTS3Listeners(new TeamspeakListener(users, queryClientId));
		List<Client> clients = api.getClients();

		if (!clients.isEmpty()) {
			for (Client client : clients) {
				users.add(new User(client.getNickname(), client.getId()));
				System.out.println(client.getNickname() + "	" + '"' + client.getId() + '"');
			}
		}
	}
}
