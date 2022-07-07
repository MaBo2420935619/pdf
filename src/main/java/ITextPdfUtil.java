
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;

import static com.itextpdf.text.pdf.PdfWriter.getInstance;

public class ITextPdfUtil {

    /**
     * @Description : 匹配pdf中的文字，进行替换
     * //        Map<String, String> map = new HashMap<>();
     * //        map.put("斑块状银屑病","****");
     * //        new ITextPdfUtil().manipulatePdf(source, target, map);
     * @Author : mabo
    */

    public static boolean manipulatePdf(String src, String dest, Map<String,String> keyss) throws Exception {
        Set<String> keys = keyss.keySet();
        Iterator<String> iterator = keys.iterator();
        List<String> keywords = new ArrayList<>();
        while (iterator.hasNext()){
            String next = iterator.next();
            keywords.add(next);
        }
        PdfReader pdfReader = null;
        PdfStamper stamper = null;
        try {
            pdfReader = new PdfReader(src);
            List<PdfBDO> list = renderText(pdfReader, keywords);
            stamper = new PdfStamper(pdfReader, new FileOutputStream(dest));
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    PdfBDO mode = list.get(i);

                    PdfContentByte canvas = stamper.getOverContent(mode.getCurPage());
                    canvas.saveState();
                    canvas.setColorFill(BaseColor.WHITE);
                    // 以左下点为原点，x轴的值，y轴的值，总宽度，总高度：
                    // canvas.rectangle(mode.getX() - 1, mode.getY(),
                    // mode.getWidth() + 2, mode.getHeight());
                    //开始覆盖内容,实际操作位置
                    canvas.rectangle(mode.getX(), mode.getY(), mode.getWidth(), mode.getHeight());
                    canvas.fill();
                    canvas.setColorFill(BaseColor.BLACK);
                    //开始写入文本
                    canvas.beginText();
                    BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
                    Font font = new Font(bf,10,Font.BOLD);
                    //设置字体和大小
                    canvas.setFontAndSize(font.getBaseFont(), 10);
                    //设置字体的输出位置
                    canvas.setTextMatrix(mode.getX(), mode.getY()+5);
                    //要输出的text
                    canvas.showText(keyss.get(keywords.get(i)) );
                    canvas.endText();
                    canvas.fill();
                    canvas.restoreState();
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stamper != null)
                stamper.close();
            if (pdfReader != null)
                pdfReader.close();
        }
        return false;
    }
    /**
     *
     * @param destPath 生成pdf文件的路劲
     * @param images	需要转换的图片路径的数组
     *                  imagesToPdf("G:/test333.pdf",new String[]{"G:/test.jpg"});
     * @throws IOException
     * @throws DocumentException
     */
    public static void imagesToPdf(String destPath, String[] images)
            throws IOException, DocumentException {
        // 第一步：创建一个document对象。
        Document document = new Document();
        document.setMargins(0, 0, 0, 0);

        // 第二步：
        // 创建一个PdfWriter实例，

        getInstance(document,new FileOutputStream(destPath));

        // 第三步：打开文档。
        document.open();

        // 第四步：在文档中增加图片。
        int len = images.length;
        for (int i = 0; i < len; i++) {
            Image img = Image.getInstance(images[i]);
            img.setAlignment(Image.ALIGN_CENTER);


            //根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
            document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
            document.newPage();
            document.add(img);
        }

        // 第五步：关闭文档。
        document.close();

    }

    /**
     * 将PDF文件转换成多张图片
     *
     * @param pdfFile PDF源文件
     * @return 图片字节数组列表
     */
    public static void pdf2images(File pdfFile) throws Exception {
        String name = pdfFile.getName();
        String[] split = name.split("\\.");
        //加载PDF
        PDDocument pdDocument = PDDocument.load(pdfFile);
        //创建PDF渲染器
        PDFRenderer renderer = new PDFRenderer(pdDocument);
        int pages = pdDocument.getNumberOfPages();
        for (int i = 0; i < pages; i++) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            //将PDF的每一页渲染成一张图片
            BufferedImage image = renderer.renderImage(i);
            ImageIO.write(image, "png", output);
            FileOutputStream fileOutputStream = new FileOutputStream("G:/"+split[0]+i+".png");
            fileOutputStream.write(output.toByteArray());
            fileOutputStream.flush();;
            fileOutputStream.close();
        }
        pdDocument.close();
    }
    /**
     * @Description : 根据关键字将其后面的文字用方框覆盖
     * @Author : mabo
    */

    public static boolean manipulatePdf(String src, String dest, List<String> keywords) throws Exception {
        File file=new File(src);
        File changeFile=new File(dest);
        PdfReader pdfReader = null;
        PdfStamper stamper = null;
        try {
            pdfReader = new PdfReader(src);
            stamper = new PdfStamper(pdfReader, new FileOutputStream(dest));
            List<PdfBDO> list = renderText(pdfReader, keywords);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    PdfBDO mode = list.get(i);

                    PdfContentByte canvas = stamper.getOverContent(mode.getCurPage());
                    canvas.saveState();
                    canvas.setColorFill(BaseColor.WHITE);
                    // 以左下点为原点，x轴的值，y轴的值，总宽度，总高度：
                    // canvas.rectangle(mode.getX() - 1, mode.getY(),
                    // mode.getWidth() + 2, mode.getHeight());
                    //开始覆盖内容,实际操作位置
                    canvas.rectangle(mode.getX()+mode.getWidth(), mode.getY(), 80, mode.getHeight());
                    canvas.fill();
                    canvas.setColorFill(BaseColor.BLACK);
                    //开始写入文本
                    canvas.beginText();
                    //BaseFont bf = BaseFont.createFont(URLDecoder.decode(CutAndPaste.class.getResource("/AdobeSongStd-Light.otf").getFile()), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
                    Font font = new Font(bf,10,Font.BOLD);
                    //设置字体和大小
                    canvas.setFontAndSize(font.getBaseFont(), 10);
                    //设置字体的输出位置
                    canvas.setTextMatrix(mode.getX()+mode.getWidth(), mode.getY()+5);
                    //要输出的text
                    canvas.showText("多退少补" );
                    canvas.endText();
                    canvas.fill();
                    canvas.restoreState();
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stamper != null)
                stamper.close();
            if (pdfReader != null)
                pdfReader.close();
        }
        return false;
    }

    public  static List<PdfBDO> renderText(PdfReader pdfReader, final List<String> keywords) {
        final List<PdfBDO> list = new ArrayList<PdfBDO>();
        try {
            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);
            int pageNum = pdfReader.getNumberOfPages();
            for (int i = 1; i <= pageNum; i++) {
                final int curPage = i;

                pdfReaderContentParser.processContent(curPage, new RenderListener() {
                    public void renderText(TextRenderInfo textRenderInfo) {
                        String text = textRenderInfo.getText();
                        if (text != null) {
                            for (int j = 0; j < keywords.size(); j++) {
                                String keyword = keywords.get(j);
                                if (text.contains(keyword)) {
                                    com.itextpdf.awt.geom.Rectangle2D.Float bound = textRenderInfo.getBaseline()
                                            .getBoundingRectange();
                                    PdfBDO lineMode = new PdfBDO();
                                    lineMode.setHeight(bound.height == 0 ? PdfBDO.defaultHeight : bound.height);
                                    lineMode.setWidth(bound.width);
                                    lineMode.setX(bound.x);
                                    lineMode.setY(bound.y - PdfBDO.fixHeight);
                                    lineMode.setCurPage(curPage);
                                    list.add(lineMode);
                                }
                            }
                        }
                    }

                    public void renderImage(ImageRenderInfo arg0) {
                    }

                    public void endTextBlock() {

                    }

                    public void beginTextBlock() {
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static File outputStream2File (ByteArrayOutputStream out,File file ) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file.getName());
        fileOutputStream.write(out.toByteArray());
        return file;
    }
    public File inputStream2File (InputStream in ,File file ) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int ch = 0;
        while ((ch = in.read()) != -1) {
            out.write(ch);
        }
        outputStream2File(out,file);
        return file;
    }
    public static InputStream File2InputStream (File file ) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        return inputStream;
    }





}


