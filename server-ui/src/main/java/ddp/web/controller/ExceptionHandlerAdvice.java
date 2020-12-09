package ddp.web.controller;

import com.alibaba.fastjson.JSON;
import ddp.beans.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 全局异常处理类
 * 拦截所有的Exception
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = {Exception.class})
    public void exception(Exception exception, HttpServletResponse response) {
        /*使用OutputStream流向客户端输出错误信息*/
        try {
            OutputStream os = response.getOutputStream();
            //通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
            response.setHeader("content-type", "text/html;charset=UTF-8");

            //异常信息
            BaseResponse<Object> resultData = BaseResponse.error();
            resultData.setMsg("异常信息：" + exception.getMessage());
            resultData.setData("异常类型：" + (exception.toString().contains(":") ?
                    exception.toString().substring(0, exception.toString().indexOf(":")) : exception.toString()));
            String data = JSON.toJSONString(resultData);

            //将字符转换成字节数组，指定以UTF-8编码进行转换
            byte[] dataByteArr = data.getBytes("UTF-8");
            //使用OutputStream流向客户端输出字节数组
            os.write(dataByteArr);
            //关闭输出流
            os.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
