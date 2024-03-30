package client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.nio.file.Path;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Config {


    private String server = "http://localhost:8080/";
    private String emailAddress = "example@gmail.com";
    private String language = "LanguagePreference?";

    public String getClientsServer() {
        return server;
    }

    public String getClientsEmailAddress() {
        return emailAddress;
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

    public void readConfigFile(){
        System.out.println(Path.of("config.json").toString());
    }
}
