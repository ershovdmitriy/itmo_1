package lab7.server.commands.HumanBeingCommands;

import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.UserManager;
import lab7.server.commands.ServerCommand;

public class Registration implements ServerCommand {
    private UserManager manager;
    public Registration(UserManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "registration";
    }

    @Override
    public String getDescr() {
        return "Регистрирует пользователя";
    }

    @Override
    public CommandResponse<?> execute(CommandRequest<?, ?> commandRequest) {
        if(manager.addUser(manager.getUser(commandRequest.getUser(), commandRequest.getPassword()))){
            return new CommandResponse<>(getName(), "Вы успешно зарегистрированы");
        }
        return new CommandResponse<>(null, "Пользователь с таким именем уже есть");
    }
}
