package util;

import java.util.ArrayList;

class Input implements Serializable {
    private String operation;
    private Channel channel;
    private User user;
    private Message message;
    private ArrayList<Channel> channelList = new ArrayList<Channel>();
    private ArrayList<User> userList = new ArrayList<User>();
    private ArrayList<Message> messageList = new ArrayList<Message>();

    public Input(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return this.operation;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public ArrayList<Channel> getChannelList() {
        return this.channelList;
    }

    public void setChannelList(ArrayList<Channel> channelList) {
        this.channelList = channelList;
    }

    public ArrayList<User> getUserList() {
        return this.userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public ArrayList<Message> getMessageList() {
        return this.messageList;
    }

    public void setMessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    
}
