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

    public String getClientsServer() {
        return server;
    }

    public String getClientsEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }


    public String getClientsLanguage() {
        return language;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void readConfigFile(){
        System.out.println(Path.of("config.json").toString());
    }
}
