
import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test2 {


    public static void main(String[] args) {
        dkzzszyfp();
    }

    /**
     * 代开增值税专用发票缴纳税款申报单
     */
    public static void dkzzszyfp() {
        // 1:建立Document对象实例
        Document document = new Document();
        try {
            // 2:建立一个PDF 写入器与document对象关联通过书写器(Writer)可以将文档写入到磁盘中
            StringBuilder filename = new StringBuilder();
            filename.append("代开增值税专用发票缴纳税款申报单").append(new SimpleDateFormat("yyyyMMddHHmm").format(new Date())).append(".pdf");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            PdfWriter.getInstance(document, new FileOutputStream(filename.toString()));
            // 3:打开文档
            document.open();

            //解决中文不显示问题
            BaseFont bfChinese = BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont, AsianFontMapper.ChineseSimplifiedEncoding_H, BaseFont.NOT_EMBEDDED);
            Font fontChina18 = new Font(bfChinese, 18);
            Font fontChina12 = new Font(bfChinese, 12);

            // 4:向文档添加内容
            // 标题
            Paragraph titleParagraph = new Paragraph("代开增值税专用发票缴纳税款申报单", fontChina18);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);// 居中
            document.add(titleParagraph);

            // 空格
            Paragraph blank1 = new Paragraph(" ");
            document.add(blank1);

            // 编号
            Chunk c1 = new Chunk("编号：", fontChina12);
            Chunk c2 = new Chunk("20140531001", fontChina12);
            Paragraph snoParagraph = new Paragraph();
            snoParagraph.add(c1);
            snoParagraph.add(c2);
            snoParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(snoParagraph);

            // 空格
            document.add(blank1);

            // 代开税务机关
            Chunk c3 = new Chunk("代开税务机关名称：青岛经济技术开发区国税局", fontChina12);
            Paragraph dkswjgParagraph = new Paragraph();
            dkswjgParagraph.add(c3);
            dkswjgParagraph.setAlignment(Element.ALIGN_LEFT);
            document.add(dkswjgParagraph);

            // 提醒
            String alert = "我单位提供的开票资料真实、完整、准确，符合有关法律、法规，否则我单位将承担一切法律责任，现申请代开增值税专用发票。";
            Paragraph alertParagraph = new Paragraph(alert, fontChina12);
            alertParagraph.setFirstLineIndent(24);// 首行缩进个2字符
            document.add(alertParagraph);

            // 填开日期
            Chunk c5 = new Chunk("填开日期：2014年05月31日", fontChina12);
            Paragraph tkrqParagraph = new Paragraph();
            tkrqParagraph.add(c5);
            tkrqParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(tkrqParagraph);

            // 空格
            document.add(blank1);

            // 表格处理
            PdfPTable table = new PdfPTable(8);// 八列
            table.setWidthPercentage(100);// 表格宽度为100%

            // 购货单位
            PdfPCell cell1 = new PdfPCell();
            cell1.setBorderWidth(0.5F);// Border宽度为1
//            cell1.setRowspan(3);// 跨三行
//            cell1.setColspan(3);// 跨三行
            cell1.setPhrase(new Paragraph("购货单位", fontChina12));
            cell1.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell1.setExtraParagraphSpace(10);
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell();
            cell2.setBorderWidth(0.5F);
            cell2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell2.setPhrase(new Paragraph("名称", fontChina12));
            table.addCell(cell2);
            PdfPCell cell3 = new PdfPCell();
            cell3.setBorderWidth(0.5F);
            cell3.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell3.setColspan(3);// 跨三列
            cell3.setPhrase(new Paragraph("名称", fontChina12));
            table.addCell(cell3);

            PdfPCell cell4 = new PdfPCell();
            cell4.setBorderWidth(0.5F);
            cell4.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell4.setPhrase(new Paragraph("税务登记号", fontChina12));
            table.addCell(cell4);
            PdfPCell cell5 = new PdfPCell();
            cell5.setBorderWidth(0.5F);
            cell5.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell5.setColspan(2);// 跨两列
            cell5.setPhrase(new Paragraph("税务登记号", fontChina12));
            table.addCell(cell5);

            PdfPCell cell6 = new PdfPCell();
            cell6.setBorderWidth(0.5F);
            cell6.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell6.setPhrase(new Paragraph("地址", fontChina12));
            table.addCell(cell6);
            PdfPCell cell7 = new PdfPCell();
            cell7.setBorderWidth(0.5F);
            cell7.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell7.setColspan(3);// 跨三列
            cell7.setPhrase(new Paragraph("地址", fontChina12));
            table.addCell(cell7);

            PdfPCell cell8 = new PdfPCell();
            cell8.setBorderWidth(0.5F);
            cell8.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell8.setPhrase(new Paragraph("开户银行", fontChina12));
            table.addCell(cell8);
            PdfPCell cell9 = new PdfPCell();
            cell9.setBorderWidth(0.5F);
            cell9.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell9.setColspan(2);// 跨两列
            cell9.setPhrase(new Paragraph("开户银行", fontChina12));
            table.addCell(cell9);

            PdfPCell cell10 = new PdfPCell();
            cell10.setBorderWidth(0.5F);
            cell10.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell10.setPhrase(new Paragraph("电话", fontChina12));
            table.addCell(cell10);
            PdfPCell cell11 = new PdfPCell();
            cell11.setBorderWidth(0.5F);
            cell11.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell11.setColspan(3);// 跨三列
            cell11.setPhrase(new Paragraph("电话", fontChina12));
            table.addCell(cell11);

            PdfPCell cell12 = new PdfPCell();
            cell12.setBorderWidth(0.5F);
            cell12.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell12.setPhrase(new Paragraph("开户银行账号", fontChina12));
            table.addCell(cell12);
            PdfPCell cell13 = new PdfPCell();
            cell13.setBorderWidth(0.5F);
            cell13.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell13.setColspan(2);// 跨两列
            cell13.setPhrase(new Paragraph("开户银行账号", fontChina12));
            table.addCell(cell13);

            // 货物或应税劳务信息
            // Row1
            PdfPCell cell14 = new PdfPCell();
            cell14.setBorderWidth(0.5F);
            cell14.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell14.setPhrase(new Paragraph("货物或应税劳务名称", fontChina12));
            table.addCell(cell14);
            PdfPCell cell15 = new PdfPCell();
            cell15.setBorderWidth(0.5F);
            cell15.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell15.setPhrase(new Paragraph("规格型号", fontChina12));
            table.addCell(cell15);
            PdfPCell cell16 = new PdfPCell();
            cell16.setBorderWidth(0.5F);
            cell16.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell16.setPhrase(new Paragraph("计量单位", fontChina12));
            table.addCell(cell16);
            PdfPCell cell17 = new PdfPCell();
            cell17.setBorderWidth(0.5F);
            cell17.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell17.setPhrase(new Paragraph("数量", fontChina12));
            table.addCell(cell17);
            PdfPCell cell18 = new PdfPCell();
            cell18.setBorderWidth(0.5F);
            cell18.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell18.setPhrase(new Paragraph("单价", fontChina12));
            table.addCell(cell18);
            PdfPCell cell19 = new PdfPCell();
            cell19.setBorderWidth(0.5F);
            cell19.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell19.setPhrase(new Paragraph("　金额", fontChina12));
            table.addCell(cell19);
            PdfPCell cell20 = new PdfPCell();
            cell20.setBorderWidth(0.5F);
            cell20.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell20.setPhrase(new Paragraph("征收率", fontChina12));
            table.addCell(cell20);
            PdfPCell cell21 = new PdfPCell();
            cell21.setBorderWidth(0.5F);
            cell21.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell21.setPhrase(new Paragraph("税额", fontChina12));
            table.addCell(cell21);
            // Row2 填写数据
            PdfPCell cell22 = new PdfPCell();
            cell22.setBorderWidth(0.5F);
            cell22.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell22.setPhrase(new Paragraph("货物或应税劳务名称", fontChina12));
            table.addCell(cell22);
            PdfPCell cell23 = new PdfPCell();
            cell23.setBorderWidth(0.5F);
            cell23.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell23.setPhrase(new Paragraph("规格型号", fontChina12));
            table.addCell(cell23);
            PdfPCell cell24 = new PdfPCell();
            cell24.setBorderWidth(0.5F);
            cell24.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell24.setPhrase(new Paragraph("计量单位", fontChina12));
            table.addCell(cell24);
            PdfPCell cell25 = new PdfPCell();
            cell25.setBorderWidth(0.5F);
            cell25.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell25.setPhrase(new Paragraph("数量", fontChina12));
            table.addCell(cell25);
            PdfPCell cell26 = new PdfPCell();
            cell26.setBorderWidth(0.5F);
            cell26.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell26.setPhrase(new Paragraph("单价", fontChina12));
            table.addCell(cell26);
            PdfPCell cell27 = new PdfPCell();
            cell27.setBorderWidth(0.5F);
            cell27.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell27.setPhrase(new Paragraph("　金额", fontChina12));
            table.addCell(cell27);
            PdfPCell cell28 = new PdfPCell();
            cell28.setBorderWidth(0.5F);
            cell28.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell28.setPhrase(new Paragraph("征收率", fontChina12));
            table.addCell(cell28);
            PdfPCell cell29 = new PdfPCell();
            cell29.setBorderWidth(0.5F);
            cell29.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell29.setPhrase(new Paragraph("税额", fontChina12));
            table.addCell(cell29);

            // 价税合计
            PdfPCell cell30 = new PdfPCell();
            cell30.setBorderWidth(0.5F);
            cell30.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell30.setPhrase(new Paragraph("价税合计（大写）", fontChina12));
            table.addCell(cell30);
            PdfPCell cell31 = new PdfPCell();
            cell31.setBorderWidth(0.5F);
            cell31.setColspan(4);// 跨四列
            cell31.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell31.setPhrase(new Paragraph("价税合计（大写）", fontChina12));
            table.addCell(cell31);
            PdfPCell cell32 = new PdfPCell();
            cell32.setBorderWidth(0.5F);
            cell32.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell32.setPhrase(new Paragraph("价税合计（小写）", fontChina12));
            table.addCell(cell32);
            PdfPCell cell33 = new PdfPCell();
            cell33.setBorderWidth(0.5F);
            cell33.setColspan(2);// 跨两列
            cell33.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell33.setPhrase(new Paragraph("价税合计（小写）", fontChina12));
            table.addCell(cell33);
            // 备注
            PdfPCell cell34 = new PdfPCell();
            cell34.setBorderWidth(0.5F);
            cell34.setMinimumHeight(40);
            cell34.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell34.setPhrase(new Paragraph("备注", fontChina12));
            table.addCell(cell34);
            PdfPCell cell35 = new PdfPCell();
            cell35.setBorderWidth(0.5F);
            cell35.setColspan(7);
            cell35.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell35.setPhrase(new Paragraph("备注", fontChina12));
            table.addCell(cell35);

            // 销货单位
            PdfPCell cell36 = new PdfPCell();
            cell36.setBorderWidth(0.5F);// Border宽度为1
            cell36.setRowspan(3);// 跨三行
            cell36.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell36.setPhrase(new Paragraph("销货单位", fontChina12));
            table.addCell(cell36);

            PdfPCell cell37 = new PdfPCell();
            cell37.setBorderWidth(0.5F);
            cell37.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell37.setPhrase(new Paragraph("名称", fontChina12));
            table.addCell(cell37);
            PdfPCell cell38 = new PdfPCell();
            cell38.setBorderWidth(0.5F);
            cell38.setColspan(3);// 跨三列
            cell38.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell38.setPhrase(new Paragraph("名称", fontChina12));
            table.addCell(cell38);

            PdfPCell cell39 = new PdfPCell();
            cell39.setBorderWidth(0.5F);
            cell39.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell39.setPhrase(new Paragraph("税务登记号", fontChina12));
            table.addCell(cell39);
            PdfPCell cell40 = new PdfPCell();
            cell40.setBorderWidth(0.5F);
            cell40.setColspan(2);// 跨两列
            cell40.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell40.setPhrase(new Paragraph("税务登记号", fontChina12));
            table.addCell(cell40);

            PdfPCell cell41 = new PdfPCell();
            cell41.setBorderWidth(0.5F);
            cell41.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell41.setPhrase(new Paragraph("地址", fontChina12));
            table.addCell(cell41);
            PdfPCell cell42 = new PdfPCell();
            cell42.setBorderWidth(0.5F);
            cell42.setColspan(3);// 跨三列
            cell42.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell42.setPhrase(new Paragraph("地址", fontChina12));
            table.addCell(cell42);

            PdfPCell cell43 = new PdfPCell();
            cell43.setBorderWidth(0.5F);
            cell43.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell43.setPhrase(new Paragraph("开户银行", fontChina12));
            table.addCell(cell43);
            PdfPCell cell44 = new PdfPCell();
            cell44.setBorderWidth(0.5F);
            cell44.setColspan(2);// 跨两列
            cell44.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell44.setPhrase(new Paragraph("开户银行", fontChina12));
            table.addCell(cell44);

            PdfPCell cell45 = new PdfPCell();
            cell45.setBorderWidth(0.5F);
            cell45.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell45.setPhrase(new Paragraph("电话", fontChina12));
            table.addCell(cell45);
            PdfPCell cell46 = new PdfPCell();
            cell46.setBorderWidth(0.5F);
            cell46.setColspan(3);// 跨三列
            cell46.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell46.setPhrase(new Paragraph("电话", fontChina12));
            table.addCell(cell46);

            PdfPCell cell47 = new PdfPCell();
            cell47.setBorderWidth(0.5F);
            cell47.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell47.setPhrase(new Paragraph("开户银行账号", fontChina12));
            table.addCell(cell47);
            PdfPCell cell48 = new PdfPCell();
            cell48.setBorderWidth(0.5F);
            cell48.setColspan(2);// 跨两列
            cell48.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell48.setPhrase(new Paragraph("开户银行账号", fontChina12));
            table.addCell(cell48);

            // 底部
            Paragraph pQz = new Paragraph("（签字）", fontChina12);
            pQz.setAlignment(Element.ALIGN_RIGHT);
            pQz.setIndentationRight(20);
            pQz.setExtraParagraphSpace(60);

            Paragraph pJbrQz = new Paragraph("申请经办人（签字）", fontChina12);
            pJbrQz.setAlignment(Element.ALIGN_RIGHT);
            pJbrQz.setIndentationRight(20);
            pJbrQz.setExtraParagraphSpace(60);

            Paragraph pDate = new Paragraph(" 年   月   日", fontChina12);
            pDate.setAlignment(Element.ALIGN_RIGHT);
            pQz.setIndentationRight(20);

            PdfPCell cellF1 = new PdfPCell();
            cellF1.setBorderWidth(0.5F);
            cellF1.setColspan(2);
            cellF1.setFixedHeight(200);//
            Paragraph p0 = new Paragraph("税务机关税款征收岗位税收完税凭证号：", fontChina12);
            cellF1.addElement(p0);
            cellF1.addElement(blank1);
            cellF1.addElement(blank1);
            cellF1.addElement(blank1);
            cellF1.addElement(pQz);
            cellF1.addElement(pDate);
            table.addCell(cellF1);

            PdfPCell cellF2 = new PdfPCell();
            cellF2.setBorderWidth(0.5F);
            cellF2.setColspan(3);
            Paragraph p1 = new Paragraph("税务机关xxx岗位", fontChina12);
            Paragraph p2 = new Paragraph("发票代码： ", fontChina12);
            Paragraph p3 = new Paragraph("发票号码： ", fontChina12);
            cellF2.addElement(p1);
            cellF2.addElement(p2);
            cellF2.addElement(p3);
            cellF2.addElement(blank1);
            cellF2.addElement(blank1);
            cellF2.addElement(pQz);
            cellF2.addElement(pDate);
            table.addCell(cellF2);

            PdfPCell cellF3 = new PdfPCell();
            cellF3.setBorderWidth(0.5F);
            cellF3.setColspan(3);
            Paragraph p4 = new Paragraph("经核对，所开发票与申报单内容一致。", fontChina12);
            p4.setFirstLineIndent(24);
            cellF3.addElement(p4);
            cellF3.addElement(blank1);
            cellF3.addElement(blank1);
            cellF3.addElement(blank1);
            cellF3.addElement(pJbrQz);
            cellF3.addElement(pDate);
            table.addCell(cellF3);

            document.add(table);

            document.add(blank1);

            //底部额外信息
            StringBuilder sb1 = new StringBuilder();
            sb1.append("申请xxx纳税人（公章）_________");
            sb1.append("法人代表_________");
            sb1.append("财务负责人_________");
            sb1.append("填写人_________");
            Paragraph pE = new Paragraph(sb1.toString(), fontChina12);
            pE.setAlignment(Element.ALIGN_CENTER);
            document.add(pE);

            document.add(blank1);

            //注
            StringBuilder sb2 = new StringBuilder();
            sb2.append("注：第一联：税务机关xxx岗位留存。");
            sb2.append("第二联：税务机关税款征收岗位留存。");
            Paragraph pZ = new Paragraph(sb2.toString(), fontChina12);
            pZ.setAlignment(Element.ALIGN_CENTER);
            document.add(pZ);

            // 5:关闭文档
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}