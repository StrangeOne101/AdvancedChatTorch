package me.mcgamer00000.act.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.PlaceholderHook;
import me.mcgamer00000.act.AdvancedChatTorch;
import org.bukkit.entity.Player;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PlaceholderLogic {

    public enum Operation {
        GREATER_THAN((d, v) -> ((double)d) > (double)v, "greater_than", "greaterthan", ">"),
        LESS_THAN((d, v) -> ((double)d) < (double)v, "less_than", "lessthan", "<"),
        GREATER_OR_EQUALS((d, v) -> ((double)d) >= (double)v, "greater_or_equals", "greaterorequals", ">="),
        LESS_OR_EQUALS((d, v) -> ((double)d) <= (double)v, "less_or_equals", "lessorequals", "<="),
        EQUALS(Object::equals,"equals", "==", ""),
        NOT_EQUALS((o, v) -> !o.equals(v),"not_equals", "notequals", "not", "!=");

        private String[] aliases;
        private BiPredicate predicate;

        Operation(BiPredicate predicate, String... aliases) {
            this.predicate = predicate;
            this.aliases = aliases;
        }

        public static Operation getFromString(String alias) {
            for (Operation op : Operation.values()) {
                for (String s : op.aliases) {
                    if (s.equalsIgnoreCase(alias)) return op;
                }
            }

            return null;
        }
    }

    String placeholder;
    String value;
    Operation operation;

    public PlaceholderLogic(String parent, String placeholder, String operation, String value) {
        this.operation = Operation.getFromString(operation);
        this.placeholder = placeholder;
        this.value = value;

        if (!PlaceholderAPI.containsPlaceholders(placeholder)) {
            AdvancedChatTorch.getInstance().getLogger().warning("Custom placeholder '" + parent + "' logic has no valid placeholder to check!");
            this.placeholder = null;
            return;
        }

        if (this.operation != Operation.EQUALS && this.operation != Operation.NOT_EQUALS) {
            try {
                Double.parseDouble(value);
            } catch (NumberFormatException e) {
                AdvancedChatTorch.getInstance().getLogger().warning("Custom placeholder '" + parent + "' logic has an invalid value for operation type " + this.operation.name() + "!");
                this.placeholder = null;
                return;
            }
        }
    }

    public boolean check(Player player) {
        if (placeholder == null) return false;


        if (this.operation.predicate.test(PlaceholderAPI.setPlaceholders(player, placeholder), this.value.contains("%") ? PlaceholderAPI.setPlaceholders(player, this.value) : this.value)) {
            return true;
        }

        return false;
    }

}
