import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {

    public static void main(String[] args) throws Exception {
        //测试特殊字符后覆盖
        String source = "G:/测试.pdf";
        String target = "G:/test222.pdf";

        //替换特殊字符
        Map<String, String> map = new HashMap<>();
        map.put("斑块状银屑病","****");
        ITextPdfUtil.manipulatePdf(source, target, map);

        //测试pdf转图片
        ITextPdfUtil.pdf2images(new File("G:\\work\\pdf脱敏\\pdf\\doc\\测试.pdf"));


        //测试特殊字符后覆盖

        List<String> keywords = new ArrayList<String>();
        keywords.add("测试");
        ITextPdfUtil.manipulatePdf(source, target, keywords);




        //图片转pdf
        ITextPdfUtil.imagesToPdf("G:/test333.pdf", new String[]{"G:/test.jpg"});
    }

}
