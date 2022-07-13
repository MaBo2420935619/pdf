import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {

    public static void main(String[] args) throws Exception {
        //测试特殊字符后覆盖

//        String source = "G:/好看的微信公众号图文是怎么做出来的.pdf";
        String source = "G:/test.pdf";
        String target = "G:/test222.pdf";


//        //测试pdf转图片
//        ITextPdfUtil.pdf2images(new File("G:\\work\\pdf脱敏\\pdf\\doc\\测试.pdf"));
//
//
        //测试特殊字符后覆盖

        List<String> keywords = new ArrayList<String>();
        keywords.add("斑块状银屑病");
        keywords.add("医师");
        ITextPdfUtil.manipulatePdf(source, target, keywords,"*");


//
//
//        //图片转pdf
//        ITextPdfUtil.imagesToPdf("G:/test333.pdf", new String[]{"G:/test.jpg"});
    }

}
