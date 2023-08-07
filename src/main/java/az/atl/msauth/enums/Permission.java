package az.atl.msauth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    AGENT_READ("agent::read"),
    AGENT_DELETE("agent::delete"),
    AGENT_UPDATE("agent::update"),
//    AGENT_CREATE("agent::create"),

    SUPERVISOR_READ("supervisor::read"),
    SUPERVISOR_DELETE("supervisor::delete"),
    SUPERVISOR_UPDATE("supervisor::update");
//    SUPERVISOR_CREATE("supervisor::create");

    private final String permission;

}
