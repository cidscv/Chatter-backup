package Server;

import java.util.ArrayList;

import util.User;
import util.Channel;
import util.Message;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class DataHandler {
    private String url;
    private String username;
    private String password;

    private Connection connection;

    DataHandler() {
        this.url = "jdbc:sqlserver://192.168.1.52:1433;";
        this.username = "project";
        this.password = "Pa55word";
        try {
            this.initializeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Database Initialized");
    }

    public void initializeConnection() throws Exception {
        try {
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // get functions

    public ArrayList<User> getAllUsers() throws Exception {
        ResultSet res = null;
        ArrayList<User> users = new ArrayList<User>();
        try {
            CallableStatement statement = this.connection.prepareCall("{call GetAllUsers()}");
            statement.execute();
            res = statement.getResultSet();

            while(res.next()) {
                int id = res.getInt("id");
                users.add(
                    new User(
                        id,
                        res.getString("username"),
                        res.getString("password"),
                        getChannelsForUser(id));
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    public ArrayList<Channel> getChannelsForUser(int userid) throws Exception {
        ResultSet res = null;
        ArrayList<Channel> channels = new ArrayList<Channel>();
        try {
            CallableStatement statement = this.connection.prepareCall("{call GetChannelsForUser(?)}");
            statement.setInt(1, userid);
            statement.execute();
            res = statement.getResultSet();

            while(res.next()) {
                channels.add(
                    new Channel(
                        res.getInt("id"),
                        res.getString("name"));
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            return channels;
        }
    }

    public ArrayList<Message> getMessagesForChannel(int channelid) throws Exception {
        ResultSet res = null;
        ArrayList<Message> messages = new ArrayList<Message>();
        try {
            CallableStatement statement = this.connection.prepareCall("{call GetMessagesForChannel(?)}");
            statement.setInt(1, channelid);
            statement.execute();
            res = statement.getResultSet();

            while(res.next()) {
                messages.add(
                    new Message(
                        res.getDate("id"),
                        res.getString("message"));
                );
            }

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            return messages;
        }
    }

    public ArrayList<String> getUserNamesForChannel(int channelid) throws Exception {
        ResultSet res = null;
        ArrayList<String> users = new ArrayList<String>();
        try {
            CallableStatement statement = this.connection.prepareCall("{call GetUsersForChannel(?)}");
            statement.setInt(1, channelid);
            statement.execute();

            while(res.next()) {
                users.add(res.getString("username"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }

    // create functions

    public Message createMessage(Message message) throws Exception {
        try {
            CallableStatement statement = this.connection.prepareCall("{call CreateMessage(?,?,?)}");

            statement.setString(1, message.getMessage());
            statement.setInt(2, message.getUser().getId());
            statement.setInt(3, message.getChannel().getId());

            statement.execute();

            message.setId(statement.getDate(1));

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            return message;
        }
    }

    public Channel createChannel(Channel channel, User user) throws Exception {
        try {
            CallableStatement statement = this.connection.prepareCall("{call CreateChannel(?,?)}");

            statement.setString(1, channel.getName());
            statement.setInt(2, user.getId());

            statement.execute();

            channel.setId(statement.getInt(1));
            user.addChannel(channel.getId());

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            return message;
        }
    }

    public User createUser(User user) throws Exception {
        try {
            CallableStatement statement = this.connection.prepareCall("{call CreateUser(?,?)}");

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getHash());

            statement.execute();

            user.setId(statement.getInt(1));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            return user;
        }
    }

    // channel-user functions

    public void addToChannel(User user, Channel channel) throws Exception {
        try {
            CallableStatement statement = this.connection.prepareCall("{call AddToChannel(?,?)}");

            statement.setString(1, user.getId());
            statement.setString(2, channel.getId());

            statement.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public User removeFromChannel(User user, Channel channel) throws Exception {
        try {
            CallableStatement statement = this.connection.prepareCall("{call RemoveFromChannel(?,?)}");

            statement.setString(1, user.getId());
            statement.setString(2, channel.getId());

            statement.execute();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}