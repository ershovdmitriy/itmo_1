package lab7.server.commands.HumanBeingCommands;

import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.commands.ServerCommand;

public class Authorization implements ServerCommand {
    @Override
    public String getName() {
        return "authorization";
    }

    @Override
    public String getDescr() {
        return "Авторизует пользователя";
    }

    @Override
    public CommandResponse<?> execute(CommandRequest<?, ?> commandRequest) {
        return new CommandResponse<>(getName(), "Вы успешно авторизованы");
    }
}
