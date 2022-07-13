import java.util.ArrayList;
import java.util.List;

public class Test1 {
    /**
     * @Author mabo
     * @Description   测试关键字匹配
     */

    public static void main(String[] args) throws Exception {
        String source = "F:/测试文件/temp2.pdf";
        String target = "F:/测试文件/test222.pdf";
        List<String> keywords = new ArrayList<String>();
        keywords.add("管");
        new ITextPdfUtil().manipulatePdf(source, target, keywords);
    }
}
