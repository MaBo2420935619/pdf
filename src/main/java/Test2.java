
public class Test2 {
    /**
     * @Author mabo
     * @Description   测试最近的文字匹配
     */
    public static void main(String[] args) throws Exception {
        String source = "F:/测试文件/temp2.pdf";
        String target = "F:/测试文件/test222.pdf";

        boolean 管理员 = ITextPdfUtil.compareText(source, target, "管理",40);
    }
}
