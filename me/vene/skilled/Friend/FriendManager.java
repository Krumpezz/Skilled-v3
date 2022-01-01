package me.vene.skilled.friend;

import java.util.HashSet;
import java.util.Set;

public class FriendManager {
  private Set<String> friends = new HashSet<String>();
  
  public void addFriend(String friend) {
    String frLowerCase = friend.toLowerCase();
    if (this.friends.contains(frLowerCase))
      return; 
    this.friends.add(frLowerCase);
  }
  
  public void removeFriend(String friend) {
    String frLowerCase = friend.toLowerCase();
    if (!this.friends.contains(frLowerCase))
      return; 
    this.friends.remove(frLowerCase);
  }
  
  public boolean isFriend(String friend) {
    return this.friends.contains(friend.toLowerCase());
  }
  
  public void nullify() {
    this.friends.clear();
    this.friends = null;
  }
  
  public Set<String> getFriendsList() {
    return this.friends;
  }
}
