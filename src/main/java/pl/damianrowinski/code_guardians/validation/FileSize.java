package pl.damianrowinski.code_guardians.validation;

public enum FileSize {
    ONE_MB(1_000_000), TEN_MB(10_000_000);

    private long fileCapacity;

    FileSize(long fileCapacity) {
        this.fileCapacity = fileCapacity;
    }

    @Override
    public String toString() {
        return "" + fileCapacity;
    }
}
