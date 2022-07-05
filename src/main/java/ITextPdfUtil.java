
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class ITextPdfUtil {

    public boolean manipulatePdf(String src, String dest, List<String> keywords) throws Exception {
        PdfReader pdfReader = null;
        PdfStamper stamper = null;
        try {
            pdfReader = new PdfReader(src);
            stamper = new PdfStamper(pdfReader, new FileOutputStream(dest));
            List<TextLineMode> list = renderText(pdfReader, keywords);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    TextLineMode mode = list.get(i);

                    PdfContentByte canvas = stamper.getOverContent(mode.getCurPage());
                    canvas.saveState();
                    canvas.setColorFill(BaseColor.BLACK);
                    // 以左下点为原点，x轴的值，y轴的值，总宽度，总高度：
                    // canvas.rectangle(mode.getX() - 1, mode.getY(),
                    // mode.getWidth() + 2, mode.getHeight());
                    //开始覆盖内容
                    canvas.rectangle(mode.getX()+mode.getWidth(), mode.getY(), 80, mode.getHeight());

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

    public List<TextLineMode> renderText(PdfReader pdfReader, final List<String> keywords) {
        final List<TextLineMode> list = new ArrayList<TextLineMode>();
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
                                    TextLineMode lineMode = new TextLineMode();
                                    lineMode.setHeight(bound.height == 0 ? TextLineMode.defaultHeight : bound.height);
                                    lineMode.setWidth(bound.width);
                                    lineMode.setX(bound.x);
                                    lineMode.setY(bound.y - TextLineMode.fixHeight);
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

    public static void main(String[] args) throws Exception {
        String source = "G:/test.pdf";
        String target = "G:/test222.pdf";
        List<String> keywords = new ArrayList<String>();
        keywords.add("处方金额");
        keywords.add("医师");
        new ITextPdfUtil().manipulatePdf(source, target, keywords);
    }
}


