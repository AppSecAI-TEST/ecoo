package ecoo.bpm.constants;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public enum TaskVariables {

    REQUEST("request"),

    ASSIGNEE("assignee");

    private final String variableName;

    TaskVariables(String variableName) {
        this.variableName = variableName;
    }

    public String variableName() {
        return variableName;
    }
}