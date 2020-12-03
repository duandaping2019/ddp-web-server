package ddp.service.file;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService{

    private Configuration configure = null;

    public Configuration getConfigure() {
        return configure;
    }

    public void setConfigure(Configuration configure) {
        this.configure = configure;
    }

    public FileServiceImpl() {
        configure = new Configuration(Configuration.VERSION_2_3_30);
        configure.setDefaultEncoding("utf-8");
        configure.setClassForTemplateLoading(this.getClass(), "/templates/word");
    }


    @Override
    public File expWord(Map<String, Object> dataMap, String templateName, String outputFilePath, HttpServletResponse response) {
        File outputFile = new File(outputFilePath);
        try (
                Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile), "utf-8");
                InputStream fin = new FileInputStream(outputFile);
                ServletOutputStream out = response.getOutputStream();
            ) {

            // 生成文件到本地磁盘
            Template template = configure.getTemplate(templateName, "utf-8");
            template.process(dataMap, writer);

            // 下载文件到客户端
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(outputFile.getName(), "UTF-8")))); // 文件下载设置

            // 通过循环将读入的Word文件的内容输出到浏览器中
            int bytesToRead = -1;
            byte[] buffer = new byte[1024];  // 缓冲区
            while((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        } finally {
            if (outputFile.exists() || outputFile != null){
                outputFile.delete();
            }
        }

        return outputFile;
    }

}
