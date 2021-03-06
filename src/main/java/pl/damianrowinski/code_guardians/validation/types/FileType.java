package pl.damianrowinski.code_guardians.validation.types;

public enum FileType {
    PDF("pdf"), CER("cer");

    private String fileExtension;

    FileType(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public String toString() {
        return fileExtension;
    }
}
