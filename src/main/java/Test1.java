import java.util.ArrayList;
import java.util.List;

public class Test1 {
    /**
     * @Author mabo
     * @Description   测试关键字匹配
     */

    public static void main(String[] args) throws Exception {
        String source = "G:/问题文件/temp.pdf";
        String target = "G:/问题文件/test222.pdf";
        List<String> keywords = new ArrayList<String>();
        keywords.add("管");
        //全字符查找替换
        ITextPdfUtil.manipulatePdf(source, target, keywords,"*****");
        //根据字符逐个匹配进行脱敏,因为部分pdf格式问题只能单个字符识别
//        ITextPdfUtil.compareText(source, target, "管理员",30,"*****");
        //根据关键字，在其后进行脱敏
//        ITextPdfUtil.manipulatePdfAfterKey(source, target, keywords,"*****");
    }
}
