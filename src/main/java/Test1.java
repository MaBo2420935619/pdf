import java.util.ArrayList;
import java.util.List;

public class Test1 {
    /**
     * @Author mabo
     * @Description   测试关键字匹配
     */

    public static void main(String[] args) throws Exception {
        //输入文件
        String source = "G:/问题文件/22.pdf";
        //输出文件
        String target = "G:/问题文件/test222.pdf";

        //字符串替换，注意设置maxDistance的值，maxDistance太小将无法正常替换文字
//        ITextPdfUtil.stringReplace(source, target, "杨荣",50,"*****");
        //关键字之后替换,仅仅支持关键字之后的100范围内的文字覆盖，超出范围将覆盖不到
        ITextPdfUtil.afterKeyReplace(source, target, "日志",50,"*****");


//        List<String> keywords = new ArrayList<String>();
//        keywords.add("管");
        //全字符查找替换
//        ITextPdfUtil.manipulatePdf(source, target, keywords,"*****");
        //根据关键字，在其后进行脱敏
//        ITextPdfUtil.manipulatePdfAfterKey(source, target, keywords,"*****");



        //根据字符逐个匹配进行脱敏,因为部分pdf格式问题只能单个字符识别
//        ITextPdfUtil.compareText(source, target, "医师",50,"*****");
        //字符逐个匹配，在其后进行脱敏
//        ITextPdfUtil.compareTextAfterKey(source, target, "医师",30,"*****");

    }
}
