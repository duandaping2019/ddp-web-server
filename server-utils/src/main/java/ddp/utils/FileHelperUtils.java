package ddp.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

public class FileHelperUtils {

    /*获取文件内容*/
    public static byte[] readResource(String path){
        byte[] data = null;
        // 该种方式为获取资源文件最优解
        ClassPathResource classPathResource = new ClassPathResource(path);
        try (InputStream in = classPathResource.getInputStream()) {
            data = new byte[in.available()];
            in.read(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;
    }

}
