package ddp.web.controller.system;

import ddp.constants.CommConstants;
import ddp.service.file.FileService;
import ddp.tools.FileHelperUtils;
import ddp.web.aop.OperLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Api(tags = "文件管理类", value = "FileRestController")
@RestController
@RequestMapping("/file")
public class FileRestController {

    @Autowired
    private FileService fileService; // 文件服务类

    /**
     * 使用FreeMarker + 模板导出word
     */
    @ApiOperation(value = "expWord", notes = "使用FreeMarker导出Word")
    @RequestMapping("/expWord")
    @OperLog(operModul = "文件管理", operType = CommConstants.GET_DATA, operDesc = "使用FreeMarker导出Word")
    public void expWord(@ApiParam(value = "语言请求参数", required = false) Locale locale,
                        @ApiParam(value = "用户响应对象", required = false) HttpServletResponse response) {
        Map<String, Object> dataMap = new HashMap<>();
        BASE64Encoder encoder = new BASE64Encoder();

        dataMap.put("goldwind_logo_real", encoder.encode(FileHelperUtils.readResource("static/images/demo/logo.png")));
        dataMap.put("goldwind_big_logo", encoder.encode(FileHelperUtils.readResource("static/images/demo/logo_big.png")));

        dataMap.put("goldwind_table_1", encoder.encode(FileHelperUtils.readResource("static/images/demo/abc.jpg")));
        dataMap.put("goldwind_table_2", encoder.encode(FileHelperUtils.readResource("static/images/demo/abc.jpg")));
        dataMap.put("goldwind_table_3", encoder.encode(FileHelperUtils.readResource("static/images/demo/abc.jpg")));

        fileService.expWord(dataMap, "demo.ftl", "G:/Temp/files/test.doc", response);

    }


}
