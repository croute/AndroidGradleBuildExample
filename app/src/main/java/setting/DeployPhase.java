package setting;

public enum DeployPhase {
    Debug(1),
    Stage(2),
    Release(3);

    int value;

    DeployPhase(int value) {
        this.value = value;
    }

    public static DeployPhase findByValue(int value) {
        switch (value) {
            case 1:
                return Debug;
            case 2:
                return Stage;
            case 3:
                return Release;
            default:
                return null;
        }
    }
}