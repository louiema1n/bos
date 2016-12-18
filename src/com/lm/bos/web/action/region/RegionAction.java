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
	 * 上传文件,批量导入region数据
	 */
	//属性驱动自动注入
	private File uploadXls;
	public void setUploadXls(File uploadXls) {
		this.uploadXls = uploadXls;
	}
	
	public String importXls() throws Exception {
		//使用flag将上传成功与否回传给页面
		String flag = "1";
		//使用POI解析xls文件
		try {
			//定位到文件位置
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(uploadXls));
			//读取Wordbook的sheet页
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			//使用BCRegion集合一次性调用regionService(打开一次事务)
			List<BcRegion> list = new ArrayList<BcRegion>();
			
			//遍历当前sheet,获取row对象
			for (Row row : sheet) {
				//忽略第一行标题
				if(row.getRowNum() == 0) {
					//结束本次循环,进行下一次循环
					continue;
				}
				//遍历当前row,获取单元格cell中的数据
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				
				//封装到BCRegion对象中
				BcRegion region = new BcRegion(id, province, city, district, postcode, null, null, null);
				
				//使用Pinyin4J工具类获取citycode城市编码(changan)
				//去掉最后一个
				city = StringUtils.substring(city, 0, city.length() - 1);
				//使用工具类将汉字转换成拼音数组
				String[] strings = PinYin4jUtils.stringToPinyin(city);
				//将数组拼接成字符串
				String citycode = StringUtils.join(strings, "");
				
				//使用Pinyin4J工具类获取shortcode简码(BJBJCA)
				province = StringUtils.substring(province, 0, province.length() - 1);
				district = StringUtils.substring(district, 0, district.length() - 1);
				//省
				String[] headProvince = PinYin4jUtils.getHeadByString(province);
				String stringPro = StringUtils.join(headProvince, "");
				//市
				String[] headCity = PinYin4jUtils.getHeadByString(city);
				String stringCity = StringUtils.join(headCity, "");
				//县
				String[] headDistrict = PinYin4jUtils.getHeadByString(district);
				String stringDis = StringUtils.join(headDistrict, "");
				
				String shortcode = stringPro + stringCity + stringDis;
				
				region.setCitycode(citycode);
				region.setShortcode(shortcode);
				
				list.add(region);
			}
			//调用saveOrUpdate方法,批量导入数据.避免重复导入
			regionService.saveOrUpdate(list);
		} catch(Exception e) {
			flag = "0";
		}
		//回传flag
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		
		return NONE;
	}

	/**
	 * 分页查询
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		regionService.queryPage(pageBean);
		//封装json
		this.writePageBean2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	/**
	 * 查询所有region
	 * @throws IOException 
	 */
	public String queryRegion() throws IOException {
		List<BcRegion> allRegion = regionService.findAll();
		//封装list到json
		this.writeList2Json(allRegion, new String[]{"province","city","district","postcode","shortcode","citycode","bcSubareas"});
		return NONE;
	}

}
