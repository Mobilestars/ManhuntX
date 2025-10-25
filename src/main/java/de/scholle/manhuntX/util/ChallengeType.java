package de.scholle.manhuntX.util;

public enum ChallengeType {
    KILL_ENDER_DRAGON("Kill Ender Dragon"),
    SURVIVE_TIME("Survive Time");

    private final String displayName;

    ChallengeType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}