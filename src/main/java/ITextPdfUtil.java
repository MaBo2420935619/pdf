import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class ITextPdfUtil {



    /**
     * @Author mabo
     * @Description
     * 由于部分pdf文字会被分割
     * 采用如下文字分割方式，寻找距离最近的字符再进行匹配
     */

    public static boolean compareText(String src, String dest, String keyword,float maxDistance) throws Exception {
        boolean success=false;
        PdfReader pdfReader = null;
        PdfStamper stamper = null;
        try {
            pdfReader = new PdfReader(src);
            stamper = new PdfStamper(pdfReader, new FileOutputStream(dest));
            char[] chars = keyword.toCharArray();
            HashMap<String, List<TextLineMode>> textMap = new HashMap<>();
            for (char c: chars) {
                String s = String.valueOf(c);
                List<TextLineMode> textLineModes = renderText(pdfReader, s);
                textMap.put(s,textLineModes);
            }
            List<TextLineMode> textLineModes = textMap.get(String.valueOf(chars[0]));
            Map<Float,Float> mapY = new HashMap<>();
            for (TextLineMode textLineMode: textLineModes) {
                //根据首字符 找出同一行的文字
                float y = textLineMode.getY();
                float x = textLineMode.getX();
                mapY.put(y,x);
            }
            Set<Float> floats = mapY.keySet();
            Iterator<Float> iterator = floats.iterator();
            HashMap<Float, Map<String,TextLineMode>> keyYMap = new HashMap<>();
            while (iterator.hasNext()){
                Float y = iterator.next();
                Float x = mapY.get(y);
                HashMap<String, TextLineMode> tMap = new HashMap<>();
                for (int i = 0; i < chars.length; i++) {
                    char c=chars[i];
                    List<TextLineMode> textLineModes1 = textMap.get(String.valueOf(c));
                    for (TextLineMode t : textLineModes1) {
                        if (t.getY()==y){
                            //判断两文字之间的具体是否符合要求
                            float x1 = t.getX();
                            float absoluteValue = getAbsoluteValue(x1, x);
                            if (absoluteValue<maxDistance){
                                Object o = tMap.get(String.valueOf(c));
                                if (o!=null){
                                    TextLineMode o1 = (TextLineMode) o;
                                    if (getAbsoluteValue(o1.getX(),x)>absoluteValue){
                                        tMap.put(String.valueOf(c),t);
                                    }
                                }
                                else {
                                    tMap.put(String.valueOf(c),t);
                                }
                            }
                        }
                    }
                }
                keyYMap.put(y,tMap);
            }
            Set<Float> keySet = keyYMap.keySet();
            Iterator<Float> iterator1 = keySet.iterator();
            while (iterator1.hasNext()){
                Float next = iterator1.next();
                Map<String,TextLineMode> map = keyYMap.get(next);
                if (map.size()==chars.length){
                    TextLineMode t = map.get(String.valueOf(chars[0]));
                    float x = t.getX();
                    float y = t.getY();
                    float width = t.getWidth() * chars.length*2;
                    float height = t.getHeight();
                    int curPage = t.getCurPage();
                    PdfContentByte canvas = stamper.getOverContent(curPage);
                    canvas.saveState();
                    canvas.setColorFill(BaseColor.BLACK);
                    // 以左下点为原点，x轴的值，y轴的值，总宽度，总高度：
                    canvas.rectangle(x, y, width, height);
                    canvas.fill();
                    canvas.restoreState();
                    success=true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stamper != null)
                stamper.close();
            if (pdfReader != null)
                pdfReader.close();
        }
        return success;
    }

    /**
     * @Author mabo
     * @Description   根据关键字，在其后对文字进行覆盖
     */
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
                    // canvas.setColorFill(BaseColor.BLUE);
                    // 以左下点为原点，x轴的值，y轴的值，总宽度，总高度：
                    canvas.rectangle(mode.getX()+mode.getWidth(), mode.getY(), 8, mode.getHeight());
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

    public static List<TextLineMode> renderText(PdfReader pdfReader, String keyword) {
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
    public static float getAbsoluteValue(float f1, float f2){
        if (f1>f2){
            return f1-f2;
        }else {
            return f2-f1;
        }
    }
}


