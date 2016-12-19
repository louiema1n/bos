package com.lm.bos.web.action.subarea;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.BcRegion;
import com.lm.bos.domain.BcSubarea;
import com.lm.bos.utils.FileUtils;
import com.lm.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<BcSubarea> {
	
	/**
	 * 新增分区
	 */
	public String add() {
		subareaService.add(this.getModel());
		return "list";
	}
	
	/**
	 * 分页查询
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		//对传递过来的查询条件进行封装
		DetachedCriteria detachedCriteria2 = pageBean.getDetachedCriteria();
		//获取查询条件
		String addresskey = this.getModel().getAddresskey();			//关键字
		BcRegion bcRegion = this.getModel().getBcRegion();	
		
		if (StringUtils.isNotBlank(addresskey)) {
			detachedCriteria2.add(Restrictions.like("addresskey", "%" + addresskey + "%"));
		}
		
		if (bcRegion != null) {
			
			String province = bcRegion.getProvince();	//省
			String city = bcRegion.getCity();			//市
			String district = bcRegion.getDistrict();	//区
			
			//使用别名进行关联表查询的区分
			detachedCriteria2.createAlias("bcRegion", "r");
			if (StringUtils.isNotBlank(province)) {
				detachedCriteria2.add(Restrictions.like("r.province", "%" + province + "%"));
			}
			if (StringUtils.isNotBlank(city)) {
				detachedCriteria2.add(Restrictions.like("r.city", "%" + city + "%"));
			}
			if (StringUtils.isNotBlank(district)) {
				detachedCriteria2.add(Restrictions.like("r.district", "%" + district + "%"));
			}
		}
		
		subareaService.queryPage(pageBean);
		//封装数据-->>注意过滤掉关联表的字段
		//因为序列化封装数据时,关联对象不会立即查询加载,会以代理对象的形式存在,导致封装数据时出错.需要在.hbm.xml关闭懒加载
		this.writePageBean2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","bcDecidedzone","bcSubareas"});
		return NONE;
	}
	
	/**
	 * 导出分区数据
	 * @throws IOException 
	 */
	public String export() throws IOException {
		//查询出所有bcSubarea
		List<BcSubarea> allSubarea = subareaService.findAll();
		//在内存中创建excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		//创建标题行
		HSSFRow headRow = sheet.createRow(0);
		//写入标题名称
		headRow.createCell(0).setCellValue("分拣编号 ");
		headRow.createCell(1).setCellValue("区域 ");
		headRow.createCell(2).setCellValue("关键字 ");
		headRow.createCell(3).setCellValue("起始号 ");
		headRow.createCell(4).setCellValue("终止号 ");
		headRow.createCell(5).setCellValue("单双号 ");
		headRow.createCell(6).setCellValue("位置 ");
		//写入内容
		for (BcSubarea subarea : allSubarea) {
			//通过当前最大行号创建新行
			HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
			row.createCell(0).setCellValue(subarea.getId());
			String region = subarea.getBcRegion().getProvince() + subarea.getBcRegion().getCity() + subarea.getBcRegion().getDistrict();
			row.createCell(1).setCellValue(region);
			row.createCell(2).setCellValue(subarea.getAddresskey());
			row.createCell(3).setCellValue(subarea.getStartnum());
			row.createCell(4).setCellValue(subarea.getEndnum());
			row.createCell(5).setCellValue(subarea.getSingle() + "");
			row.createCell(6).setCellValue(subarea.getPosition());
		}
		
		//一个流两个头
		String fileName = "分区数据.xls";
		//处理文件名中文的问题
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		fileName = FileUtils.encodeDownloadFilename(fileName, agent);
		//输出流
		ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
		//两个头
		//通过文件名动态获取mimeType
		String mimeType = ServletActionContext.getServletContext().getMimeType(fileName);
		ServletActionContext.getResponse().setContentType(mimeType);
		
		ServletActionContext.getResponse().setHeader("content-disposition", "attchment;filename=" + fileName);
		//通过流输出
		workbook.write(outputStream);
		
		return NONE;
	}
	
	/**
	 * 查询没有定区的分区
	 * @throws IOException 
	 */
	public String listSubarea() throws IOException{
		List<BcSubarea> list = subareaService.queryNoBcDecidedzone();
		this.writeList2Json(list, new String[]{"bcDecidedzone","bcRegion"});
		return NONE;
	}
}
