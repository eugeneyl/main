package seedu.finance.logic.commands;

import static seedu.finance.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_CATEGORY;

import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;
import seedu.finance.model.category.Category;
import seedu.finance.model.record.Amount;

/**
 * Allocates a certain amount to a category
 */
public class AllocateCommand extends Command {

    public static final String COMMAND_WORD = "allocate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set the budget amount for a particular category "
            + "by indicating the amount and the category.\n"
            + "Existing budget for the category will be overwritten by the input\n"
            + "Parameters: " + PREFIX_AMOUNT + "AMOUNT " + PREFIX_CATEGORY + "CATEGORY\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_AMOUNT + "100.10 "
            + PREFIX_CATEGORY + "Food ";

    public static final String MESSAGE_ARGUMENTS = "Amount: %1$s, Category: %2$s";

    private final Amount amount;
    private final Category category;

    public AllocateCommand(Amount amount, Category category) {
        requireAllNonNull(amount, category);

        this.amount = amount;
        this.category = category;
    }

    @Override
    public CommandResult execute(Model mode, CommandHistory history) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, amount, category));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handle nulls
        if (!(other instanceof AllocateCommand)) {
            return false;
        }

        //state check

        AllocateCommand e = (AllocateCommand) other;
        return amount.equals(e.amount) && category.equals(e.category);
    }


}