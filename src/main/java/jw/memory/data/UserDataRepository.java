package jw.memory.data;

import jw.api.data.repositories.RepositoryBase;
import jw.api.utilites.FileHelper;

import java.util.Optional;

public class UserDataRepository extends RepositoryBase<UserData>
{
    public UserDataRepository()
    {
        super(FileHelper.PluginPath(), UserData.class);
    }

    @Override
    public UserData getOne(String id)
    {
        Optional<UserData> data = content.stream().filter(p -> p.playerUUID.equalsIgnoreCase(id)).findFirst();
        return data.orElseGet(this::CreateEmpty);
    }
}
