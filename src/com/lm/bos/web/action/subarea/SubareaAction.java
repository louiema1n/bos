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
	 * ��������
	 */
	public String add() {
		subareaService.add(this.getModel());
		return "list";
	}
	
	/**
	 * ��ҳ��ѯ
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		//�Դ��ݹ����Ĳ�ѯ�������з�װ
		DetachedCriteria detachedCriteria2 = pageBean.getDetachedCriteria();
		//��ȡ��ѯ����
		String addresskey = this.getModel().getAddresskey();			//�ؼ���
		BcRegion bcRegion = this.getModel().getBcRegion();	
		
		if (StringUtils.isNotBlank(addresskey)) {
			detachedCriteria2.add(Restrictions.like("addresskey", "%" + addresskey + "%"));
		}
		
		if (bcRegion != null) {
			
			String province = bcRegion.getProvince();	//ʡ
			String city = bcRegion.getCity();			//��
			String district = bcRegion.getDistrict();	//��
			
			//ʹ�ñ������й������ѯ������
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
		//��װ����-->>ע����˵���������ֶ�
		//��Ϊ���л���װ����ʱ,�������󲻻�������ѯ����,���Դ���������ʽ����,���·�װ����ʱ����.��Ҫ��.hbm.xml�ر�������
		this.writePageBean2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","bcDecidedzone","bcSubareas"});
		return NONE;
	}
	
	/**
	 * ������������
	 * @throws IOException 
	 */
	public String export() throws IOException {
		//��ѯ������bcSubarea
		List<BcSubarea> allSubarea = subareaService.findAll();
		//���ڴ��д���excel�ļ�
		HSSFWorkbook workbook = new HSSFWorkbook();
		//����sheetҳ
		HSSFSheet sheet = workbook.createSheet("��������");
		//����������
		HSSFRow headRow = sheet.createRow(0);
		//д���������
		headRow.createCell(0).setCellValue("�ּ��� ");
		headRow.createCell(1).setCellValue("���� ");
		headRow.createCell(2).setCellValue("�ؼ��� ");
		headRow.createCell(3).setCellValue("��ʼ�� ");
		headRow.createCell(4).setCellValue("��ֹ�� ");
		headRow.createCell(5).setCellValue("��˫�� ");
		headRow.createCell(6).setCellValue("λ�� ");
		//д������
		for (BcSubarea subarea : allSubarea) {
			//ͨ����ǰ����кŴ�������
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
		
		//һ��������ͷ
		String fileName = "��������.xls";
		//�����ļ������ĵ�����
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		fileName = FileUtils.encodeDownloadFilename(fileName, agent);
		//�����
		ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
		//����ͷ
		//ͨ���ļ�����̬��ȡmimeType
		String mimeType = ServletActionContext.getServletContext().getMimeType(fileName);
		ServletActionContext.getResponse().setContentType(mimeType);
		
		ServletActionContext.getResponse().setHeader("content-disposition", "attchment;filename=" + fileName);
		//ͨ�������
		workbook.write(outputStream);
		
		return NONE;
	}
	
	/**
	 * ��ѯû�ж����ķ���
	 * @throws IOException 
	 */
	public String listSubarea() throws IOException{
		List<BcSubarea> list = subareaService.queryNoBcDecidedzone();
		this.writeList2Json(list, new String[]{"bcDecidedzone","bcRegion"});
		return NONE;
	}
}
