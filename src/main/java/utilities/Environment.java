package utilities;

import org.aeonbits.owner.Config;

@Config.Sources({"file:environmentConfig/dev.properties"})
public interface Environment extends Config {
    @Key("app.Url")
    String appUrl();
    @Key("App.User")
    String appUser();
    @Key("app.Password")
    String appPassword();
    @Key("DB.Host")
    String dbHost();
    @Key("DB.User")
    String dbUser();
    @Key("DB.Password")
    String dbPassword();
}
