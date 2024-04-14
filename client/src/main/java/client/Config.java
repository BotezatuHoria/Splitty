package client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.nio.file.Path;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Config {


    private String server;
    private String emailAddress;
    private String language;
    private String password;

    /**
     * gets client server.
     * @return string representation of server.
     */
    public String getClientsServer() {
        return server;
    }

    /**
     * gets client address.
     * @return string representation of address.
     */
    public String getClientsEmailAddress() {
        return emailAddress;
    }


    /**
     * gets client password.
     * @return string representation of password.
     */
    public String getPassword() {
        return password;
    }


    /**
     * gets client language.
     * @return string representation of language.
     */
    public String getClientsLanguage() {
        return language;
    }

    /**
     * sets client server.
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * sets client email.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * sets client language.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * sets client password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * reads config file.
     */
    public void readConfigFile(){
        System.out.println(Path.of("config.json").toString());
    }
}
