package NewsPusherModule.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

/**
 * <类的详细说明：>
 *
 * @Author: Huanghai
 * @Version: 2017/1/3
 **/
public class FileWriterUtil
{
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("conf");
    public static void writeLog(String log) throws IOException {
        File file = new File(resourceBundle.getString("pushRecordsPath"));
        FileWriter fw = new FileWriter(file,true);
        /**
         * 为了提高写入的效率，使用了字符流的缓冲区。
         * 创建了一个字符写入流的缓冲区对象，并和指定要被缓冲的流对象相关联。
         */
        PrintWriter printWriter= new PrintWriter(fw);

        printWriter.write(log+"\n");
        fw.flush();
        printWriter.flush();
        //关闭缓冲区,同时关闭了fw流对象
        fw.close();
    }
}
