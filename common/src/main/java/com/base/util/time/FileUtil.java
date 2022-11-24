//package com.base.util.time;
//import static com.trs.common.base.PreConditionCheck.checkNotNull;
//import static com.trs.common.utils.StringUtils.isEmpty;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//
//import com.trs.common.base.PreConditionCheck;
//
//import io.vavr.Tuple2;
//
///**
// * FileUtil
// *
// * @description: FileUtil
// * @author: deng.youxu
// * @since: 2020-01-03 17:54
// **/
//public class FileUtil {
//
//	/**
//	 * 导出文本至docx文件
//	 *
//	 * @param pureTextStringList 文本内容，1st表示标题内容，2nd表示正文内容
//	 * @param localPath 本地路径
//	 * @return 本地文件
//	 */
//	public static File exportMeetingRecordsToWordFile(List<Tuple2<String, List<String>>> pureTextStringList, String localPath) {
//		try {
//			PreConditionCheck.checkArgument(!isEmpty(localPath));
//			File file = new File(localPath);
//			if (file.exists()) {
//				file.delete();
//			} else {
//				file.createNewFile();
//			}
//			checkNotNull(pureTextStringList, "导出内容不能为空");
//			XWPFDocument doc = new XWPFDocument();
//			XWPFParagraph paragraph = doc.createParagraph();
//			paragraph.setAlignment(ParagraphAlignment.LEFT);
//			pureTextStringList.forEach((Tuple2<String, List<String>> text) -> {
//				String title = text._1;
//				List<String> content = text._2;
//				//一个XWPFRun代表具有相同属性的一个区域。
//				XWPFRun run = paragraph.createRun();
//				run.setFontFamily("Heiti SC Medium");
//				run.setFontSize(13);
//				run.setText(title); //设置标题
//				run.addCarriageReturn();
//				XWPFRun run1 = paragraph.createRun();
//				run1.setFontSize(11);
//				run1.setFontFamily("等线", XWPFRun.FontCharRange.eastAsia);
//				run1.setText("会议纪要:");
//				run1.addCarriageReturn();
//				AtomicInteger index = new AtomicInteger(0);
//				XWPFRun run2 = paragraph.createRun();
//				run2.setFontSize(11);
//				run2.setFontFamily("FangSong");
//				run2.setBold(false);
//				content.forEach((String c) -> {
//					paragraph.setStyle("7");
//					String contentWithIndex = index.incrementAndGet() + "、" + c;
//					run2.setText(contentWithIndex);
//					run2.addCarriageReturn();
//				});
//			});
//			FileOutputStream out = new FileOutputStream(file);
//			doc.write(out);
//			out.close();
//			return file;
//		} catch (Exception ex) {
//
//		}
//		return null;
//	}
//
////	/**
////	 * 导出统计数据至excel
////	 *
////	 * @param staticDataList 文本内容，1st表示标题内容，2nd表示正文内容
////	 * @param localPath 本地路径
////	 * @return 本地文件
////	 */
////	public static File exportStaticDataToExcelFile(List<Tuple2<String, Integer>> staticDataList, String localPath, String staticsRange) {
////		try {
////			PreConditionCheck.checkArgument(!isEmpty(localPath));
////			File file = new File(localPath);
////			if (file.exists()) {
////				file.delete();
////			} else {
////				file.createNewFile();
////			}
////			checkNotNull(staticDataList, "导出内容不能为空");
////			XWPFDocument doc = new XWPFDocument();
////			XWPFParagraph paragraph = doc.createParagraph();
////			paragraph.setAlignment(ParagraphAlignment.LEFT);
////			staticDataList.forEach((Tuple2<String, Integer> staticData) -> {
////				String title = text._1;
////				List<String> content = text._2;
////				//一个XWPFRun代表具有相同属性的一个区域。
////				XWPFRun run = paragraph.createRun();
////				run.setFontFamily("Heiti SC Medium");
////				run.setFontSize(13);
////				run.setText(title); //设置标题
////				run.addCarriageReturn();
////				XWPFRun run1 = paragraph.createRun();
////				run1.setFontSize(11);
////				run1.setFontFamily("等线", XWPFRun.FontCharRange.eastAsia);
////				run1.setText("会议纪要:");
////				run1.addCarriageReturn();
////				AtomicInteger index = new AtomicInteger(0);
////				XWPFRun run2 = paragraph.createRun();
////				run2.setFontSize(11);
////				run2.setFontFamily("FangSong");
////				run2.setBold(false);
////				content.forEach((String c) -> {
////					paragraph.setStyle("7");
////					String contentWithIndex = index.incrementAndGet() + "、" + c;
////					run2.setText(contentWithIndex);
////					run2.addCarriageReturn();
////				});
////			});
////			FileOutputStream out = new FileOutputStream(file);
////			doc.write(out);
////			out.close();
////			return file;
////		} catch (Exception ex) {
////
////		}
////		return null;
////	}
//}
