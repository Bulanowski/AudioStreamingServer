package networking;

import java.util.ArrayList;
import java.util.Date;

import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;

import model.User;

public class TeamspeakListener extends TS3EventAdapter {
	ArrayList<User> users;
	int queryClientId;
	
	public  TeamspeakListener(ArrayList<User> users, int queryClientId ) {
		this.users = new ArrayList<>();
		this.queryClientId = queryClientId;
	}
	
	
	public ArrayList<User> getUsers() {
		return users;
	}

	@Override
	public void onClientJoin(ClientJoinEvent e) {
		if (e.getInvokerId() != queryClientId) {
			System.out.print(e.getClientNickname()+" joined on ");
			System.out.println(new Date());
			users.add(new User(e.getClientNickname(), e.getClientId()));
		}
	}
	
	@Override
	public void onClientLeave(ClientLeaveEvent e) {
		if (e.getInvokerId() != queryClientId) {
			
			for (int i = 0; i < users.size(); i++) {
				if(users.get(i).getId()==e.getClientId()) {
					System.out.print(users.get(i).getName()+" left on ");
					System.out.println(new Date());
					users.remove(users.get(i));
				}
			}
			
		}
	}

}
