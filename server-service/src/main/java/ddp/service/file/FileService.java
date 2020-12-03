package ddp.service.file;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * 文件服务管理类
 */
public interface FileService {
    /**
     * 导出Word（FreeMarker+模板）
     * @param dataMap 模板数据集合
     * @param templateName 模板名称
     * @param outputFilePath 输出文件路径
     * @param response 客户端响应对象
     */
    File expWord(Map<String, Object> dataMap, String templateName, String outputFilePath, HttpServletResponse response);

}
