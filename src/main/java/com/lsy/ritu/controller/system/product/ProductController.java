package com.lsy.ritu.controller.system.product;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils; 
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody; 
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lsy.ritu.controller.base.BaseController;
import com.lsy.ritu.entity.Page;
import com.lsy.ritu.entity.system.Role;
import com.lsy.ritu.service.system.product.ProductService;
import com.lsy.ritu.util.AppUtil;
import com.lsy.ritu.util.Const; 
import com.lsy.ritu.util.FileDownload;
import com.lsy.ritu.util.FileUpload;
import com.lsy.ritu.util.GetPinyin;
import com.lsy.ritu.util.Jurisdiction;
import com.lsy.ritu.util.ObjectExcelRead;
import com.lsy.ritu.util.ObjectExcelView;
import com.lsy.ritu.util.PageData; 
import com.lsy.ritu.util.PathUtil;
import com.lsy.ritu.util.Tools;


@Controller
@RequestMapping(value="/product")
public class ProductController  extends BaseController{

	String menuUrl = "product/list.do"; //菜单地址(权限用)
	@Resource(name="productService")
	private ProductService productService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(PrintWriter out) throws Exception{
		logBefore(logger, "新增product");  
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		logBefore(logger, "add"); 
		pd.put("PName", pd.getString("PName"));					//产品名称	
		pd.put("PVer", pd.getString("PVer"));				//版本
		pd.put("ReleaseTime", pd.getString("ReleaseTime"));	//创建时间 
		pd.put("Remark", pd.getString("Remark"));						//备注
		logBefore(logger, "add:"+pd.getString("PName")+"-"+ pd.getString("PVer")+"-"+pd.getString("ReleaseTime")+"-"+pd.getString("Remark")); 
		
		 
			if(Jurisdiction.buttonJurisdiction(menuUrl, "add")){productService.save(pd);} //判断新增权限
			mv.addObject("result","ok");
		 
		mv.setViewName("save_result");
		return mv;
		
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除product");
		PageData pd = new PageData();
		try{
			
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){productService.delete(pd);}
			out.write("success");
			out.close();
			  
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(
			HttpServletRequest request, 
			@RequestParam(value="tpz",required=false) String tpz,
			@RequestParam(value="ID",required=false) String ID,
			@RequestParam(value="PNAME",required=false) String PNAME,
			@RequestParam(value="PVER",required=false) String PVER,
			@RequestParam(value="RELEASETIME",required=false) Date RELEASETIME,
			@RequestParam(value="REMARK",required=false) String REMARK
			) throws Exception{
		logBefore(logger, "修改product");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(Jurisdiction.buttonJurisdiction(menuUrl, "edit")){
			pd.put("ID", ID);		//ID
			pd.put("PNAME", PNAME);					//name
			pd.put("PVER", PVER);			//ver
			pd.put("RELEASETIME", RELEASETIME);			//time
			pd.put("REMARK", REMARK);						//备注
			
			if(null == tpz){tpz = "";}
		 
			productService.edit(pd);				//执行修改数据库
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表product");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			
			String KEYW = pd.getString("keyword");
			
			if(null != KEYW && !"".equals(KEYW)){
				KEYW = KEYW.trim();
				pd.put("KEYW", KEYW);
			}
		 
			
			page.setPd(pd);
			List<PageData>	varList = productService.list(page);	//列出product列表
			mv.setViewName("system/product/product_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增product页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("system/product/product_add");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改product页面");
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, "product goedit ModelAndView:"+mv.toString());
		PageData pd = new PageData();
		pd = this.getPageData();
		logBefore(logger, "product goedit:"+pd.toString());
		try {
			pd = productService.findById(pd);	//根据ID读取
			mv.setViewName("system/product/product_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除product");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			if(Jurisdiction.buttonJurisdiction(menuUrl, "del")){
				List<PageData> pdList = new ArrayList<PageData>();
				 
				String DATA_IDS = pd.getString("DATA_IDS");
				if(null != DATA_IDS && !"".equals(DATA_IDS)){
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					 
					productService.deleteAll(ArrayDATA_IDS);
					pd.put("msg", "ok");
				}else{
					pd.put("msg", "no");
				}
				pdList.add(pd);
				map.put("list", pdList);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出product到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("编号");	//1
			titles.add("产品名称");	//2
			titles.add("版本号");	//3
			titles.add("发布时间");	//4
			titles.add("备注");	//5 
			dataMap.put("titles", titles);
			List<PageData> varOList = productService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", String.valueOf(varOList.get(i).getInteger("Id")));	//1
				vpd.put("var2", varOList.get(i).getString("PName"));	//2
				vpd.put("var3", varOList.get(i).getString("PVer"));	//3
				vpd.put("var4", String.valueOf(varOList.get(i).getDateTime("ReleaseTime")));	//4
				vpd.put("var5", varOList.get(i).getString("Remark"));	//5 
				 
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	 
	
	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/product/uploadexcel");
		return mv;
	}
	
	/**
	 * 下载模版
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Products.xls", "Products.xls");
		
	}
	
	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			//String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String filePath = "./" + Const.FILEPATHFILE;			
			logBefore(logger, "readExcel PathUtil.getClasspath() :"+PathUtil.getClasspath()+":"+Const.FILEPATHFILE);
			
			logBefore(logger, "readExcel file :"+filePath+":");
			String fileName =  FileUpload.fileUp(file, filePath, "productexcel");							//执行上传
			logBefore(logger, "readExcel file :"+filePath+":"+fileName);
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);	//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			
			/*存入数据库操作======================================*/
			pd.put("Id", "");					//Id
			pd.put("PName", "");					//产品名称	
			pd.put("PVer", "");				//版本
			pd.put("ReleaseTime", "");	//创建时间 
			pd.put("Remark", "");						//备注
			 
			 
			/**
			 * var0 :Id
			 * var1 :名称
			 * var2 :版本
			 * var3 :时间
			 * var4 :备注 
			 */
			for(int i=0;i<listPd.size();i++){		
			 
				//String Id =  listPd.get(i).getString("var0");
				logBefore(logger, "listPd var1:"+listPd.get(i).getString("var1"));
				pd.put("Id",Integer.valueOf(listPd.get(i).getString("var1")));				
				pd.put("PName", listPd.get(i).getString("var1"));							//名称
				  
				if(productService.findByPN(pd) != null){										//判断名称是否重复
				 
					pd.put("PName", listPd.get(i).getString("var1")+Tools.getRandomNum());	
				}
				pd.put("PVer", listPd.get(i).getString("var2"));	//版本
				pd.put("ReleaseTime", Date.parse(listPd.get(i).getString("var3")));	//时间
				pd.put("Remark", listPd.get(i).getString("var4"));	//备注		
				
				if(productService.findById(pd)!=null)
				{
					productService.update(pd);
				}
				else
				{
				productService.save(pd);
				}
			}
			/*存入数据库操作======================================*/
			
			mv.addObject("msg","success");
		}
		
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
