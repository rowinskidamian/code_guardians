package pl.damianrowinski.code_guardians.assemblers;

import org.springframework.stereotype.Service;

@Service
public class PathAssembler {

    public String getAbsoluteFilePath(String fileName, String pathName) {
        return pathName + "\\" + fileName;
    }

}
