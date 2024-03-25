package client.utils;


public class LanguageSingleton {
    private static final LanguageSingleton instance = new LanguageSingleton();
    private String code;

    public LanguageSingleton() {
        this.code = "en";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
