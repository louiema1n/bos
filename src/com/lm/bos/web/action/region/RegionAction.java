package com.lm.bos.web.action.region;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.BcRegion;
import com.lm.bos.service.IRegionService;
import com.lm.bos.utils.PinYin4jUtils;
import com.lm.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<BcRegion> {
	
	@Autowired
	private IRegionService regionService;

	/**
	 * �ϴ��ļ�,��������region����
	 */
	//���������Զ�ע��
	private File uploadXls;
	public void setUploadXls(File uploadXls) {
		this.uploadXls = uploadXls;
	}
	
	public String importXls() throws Exception {
		//ʹ��flag���ϴ��ɹ����ش���ҳ��
		String flag = "1";
		//ʹ��POI����xls�ļ�
		try {
			//��λ���ļ�λ��
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(uploadXls));
			//��ȡWordbook��sheetҳ
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			//ʹ��BCRegion����һ���Ե���regionService(��һ������)
			List<BcRegion> list = new ArrayList<BcRegion>();
			
			//������ǰsheet,��ȡrow����
			for (Row row : sheet) {
				//���Ե�һ�б���
				if(row.getRowNum() == 0) {
					//��������ѭ��,������һ��ѭ��
					continue;
				}
				//������ǰrow,��ȡ��Ԫ��cell�е�����
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				
				//��װ��BCRegion������
				BcRegion region = new BcRegion(id, province, city, district, postcode, null, null, null);
				
				//ʹ��Pinyin4J�������ȡcitycode���б���(changan)
				//ȥ�����һ��
				city = StringUtils.substring(city, 0, city.length() - 1);
				//ʹ�ù����ཫ����ת����ƴ������
				String[] strings = PinYin4jUtils.stringToPinyin(city);
				//������ƴ�ӳ��ַ���
				String citycode = StringUtils.join(strings, "");
				
				//ʹ��Pinyin4J�������ȡshortcode����(BJBJCA)
				province = StringUtils.substring(province, 0, province.length() - 1);
				district = StringUtils.substring(district, 0, district.length() - 1);
				//ʡ
				String[] headProvince = PinYin4jUtils.getHeadByString(province);
				String stringPro = StringUtils.join(headProvince, "");
				//��
				String[] headCity = PinYin4jUtils.getHeadByString(city);
				String stringCity = StringUtils.join(headCity, "");
				//��
				String[] headDistrict = PinYin4jUtils.getHeadByString(district);
				String stringDis = StringUtils.join(headDistrict, "");
				
				String shortcode = stringPro + stringCity + stringDis;
				
				region.setCitycode(citycode);
				region.setShortcode(shortcode);
				
				list.add(region);
			}
			//����saveOrUpdate����,������������.�����ظ�����
			regionService.saveOrUpdate(list);
		} catch(Exception e) {
			flag = "0";
		}
		//�ش�flag
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		
		return NONE;
	}

	/**
	 * ��ҳ��ѯ
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		regionService.queryPage(pageBean);
		//��װjson
		this.writePageBean2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	/**
	 * ��ѯ����region
	 * @throws IOException 
	 */
	public String queryRegion() throws IOException {
		List<BcRegion> allRegion = regionService.findAll();
		//��װlist��json
		this.writeList2Json(allRegion, new String[]{"province","city","district","postcode","shortcode","citycode","bcSubareas"});
		return NONE;
	}

}
