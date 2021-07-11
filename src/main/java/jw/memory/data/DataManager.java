package jw.memory.data;

import jw.api.data.repositories.RepositoryManager;

public class DataManager extends RepositoryManager
{
    private Settings settings;
    private UserDataRepository userDataRepository;

    public DataManager()
    {
        settings = new Settings();
        userDataRepository = new UserDataRepository();

        this.AddObject(settings);
        this.AddRepository(userDataRepository);
    }
    public Settings getSettings() {
        return settings;
    }
    public UserDataRepository getUserDataRepository() {
        return userDataRepository;
    }
}
