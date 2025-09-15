package lab7.client.commands.HumanBeingCommands;

import lab7.client.commands.ClientCommand;
import lab7.common.exception.CommandException;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;

public class Registration extends ClientCommand {

    @Override
    public String getName() {
        return "registration";
    }

    @Override
    public boolean checkArgument() {
        if (getArgument() == null) {
            return true;
        } else {
            System.out.println("registration не имеет аргументов!");
            return false;
        }
    }

    @Override
    public CommandRequest<?, ?> buildRequest() throws CommandException {
        return new CommandRequest<>(getName());
    }

    @Override
    public void read(CommandResponse<?> commandResponse) throws CommandException {
        System.out.println(commandResponse.getMessage());
    }
}
