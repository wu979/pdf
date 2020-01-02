package com.custom.pdf.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @des:
 * @author: wsw
 * @email: 18683789594@163.com
 * @date: 2020/1/2 21:57
 */
public class PDFUtil {

    public static void creatPdf(Map<String, Object> map, String filePath) {
        try {
            String path = PDFUtil.class.getClassLoader().getResource("").getPath();
            BaseFont bf = BaseFont.createFont(path + "fonts/" + "SIMLI.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            FileOutputStream out = new FileOutputStream(filePath);// 输出流
            PdfReader reader = new PdfReader("H://create_pdf.pdf");// 读取pdf模板
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, bos);
            stamper.setFormFlattening(true);
            AcroFields form = stamper.getAcroFields();
            // 文字类的内容处理
            Map<String, String> datemap = (Map<String, String>) map.get("datemap");
            form.addSubstitutionFont(bf);
            for (String key : datemap.keySet()) {
                String value = datemap.get(key);
                form.setField(key, value);
            }
            // 图片类的内容处理
            Map<String, String> imgmap = (Map<String, String>) map.get("imgmap");
            for (String key : imgmap.keySet()) {
                String value = imgmap.get(key);
                String imgpath = value;
                int pageNo = form.getFieldPositions(key).get(0).page;
                Rectangle signRect = form.getFieldPositions(key).get(0).position;
                float x = signRect.getLeft();
                float y = signRect.getBottom();
                // 根据路径读取图片
                Image image = Image.getInstance(imgpath);
                // 获取图片页面
                PdfContentByte under = stamper.getOverContent(pageNo);
                // 图片大小自适应
                image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                // 添加图片
                image.setAbsolutePosition(x, y);
                under.addImage(image);
            }
            // 表格类
            Map<String, List<List<String>>> listMap = (Map<String, List<List<String>>>) map.get("list");
            for (String key : listMap.keySet()) {
                List<List<String>> lists = listMap.get(key);
                int pageNo = form.getFieldPositions(key).get(0).page;
                PdfContentByte pcb = stamper.getOverContent(pageNo);
                Rectangle signRect = form.getFieldPositions(key).get(0).position;
                //表格位置
                int column = lists.get(0).size();
                int row = lists.size();
                PdfPTable table = new PdfPTable(column);
                float tatalWidth = signRect.getRight() - signRect.getLeft() - 1;
                int size = lists.get(0).size();
                float width[] = new float[size];
                for (int i = 0; i < size; i++) {
                    if (i == 0) {
                        width[i] = 60f;
                    } else {
                        width[i] = (tatalWidth - 60) / (size - 1);
                    }
                }
                table.setTotalWidth(width);
                table.setLockedWidth(true);
                table.setKeepTogether(true);
                table.setSplitLate(false);
                table.setSplitRows(true);
                Font FontProve = new Font(bf, 10, 0);
                //表格数据填写
                for (int i = 0; i < row; i++) {
                    List<String> list = lists.get(i);
                    for (int j = 0; j < column; j++) {
                        Paragraph paragraph = new Paragraph(String.valueOf(list.get(j)), FontProve);
                        PdfPCell cell = new PdfPCell(paragraph);
                        cell.setBorderWidth(1);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setLeading(0, (float) 1.4);
                        table.addCell(cell);
                    }
                }
                table.writeSelectedRows(0, -1, signRect.getLeft(), signRect.getTop(), pcb);
            }
            stamper.setFormFlattening(true);// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            int pageNum = reader.getNumberOfPages();
            for (int i = 1; i <= pageNum; i++) {
                PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), i);
                copy.addPage(importPage);
            }
            doc.close();
        } catch (IOException e) {
            System.out.println(e);
        } catch (DocumentException e) {
            System.out.println(e);
        }
    }


    public static void main(String[] args) {
        //文字类
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("fill_1", "979");
        dataMap.put("fill_2", "26");
        dataMap.put("fill_3", "JAVA");
        dataMap.put("fill_4", "篮球");

        //图片
        //String knowImgPath = "D:\\upload\\report\\knowImg.png";
        //Map<String, String> imgMap = new HashMap<String, String>();
        //imgMap.put("knowImg", knowImgPath);

        //表格 一行数据是一个list
        List<String> list = new ArrayList<String>();
        list.add("监察人");
        list.add("是否合格");
        list.add("是否违规");
        list.add("是否整改");

        List<String> list2 = new ArrayList<String>();
        list2.add("张三");
        list2.add("张三是");
        list2.add("张三否");
        list2.add("张三否");

        List<String> list3 = new ArrayList<String>();
        list3.add("李四");
        list3.add("李四否");
        list3.add("李四是");
        list3.add("李四是");

        List<String> list4 = new ArrayList<String>();
        list4.add("王五");
        list4.add("王五否");
        list4.add("王五是");
        list4.add("王五否");

        List<List<String>> List = new ArrayList<List<String>>();
        List.add(list);
        List.add(list2);
        List.add(list3);
        List.add(list4);

        Map<String, List<List<String>>> listMap = new HashMap<String, List<List<String>>>();
        listMap.put("Text1", List);

        Map<String, Object> o = new HashMap<String, Object>();
        o.put("datemap", dataMap);
        //o.put("imgmap", imgMap);
        o.put("list", listMap);

        String filePath = "H://new.pdf";
        creatPdf(o, filePath);
    }
}
