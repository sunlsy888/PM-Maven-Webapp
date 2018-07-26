package com.lsy.ritu.service.system.project;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lsy.ritu.dao.DaoSupport;
import com.lsy.ritu.entity.Page;
import com.lsy.ritu.entity.system.Dictionaries;
import com.lsy.ritu.entity.system.SoftVer;
import com.lsy.ritu.util.PageData;


@Service("softverService")
public class SoftVerService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("SoftVerMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("SoftVerMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("SoftVerMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<SoftVer> listSoftVer(String PId)throws Exception{
		return (List<SoftVer>)dao.findForList("SoftVerMapper.datalistSoftVer", PId);
	}
 
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SoftVerMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SoftVerMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SoftVerMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SoftVerMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	* 批量获取
	*/
	public List<PageData> getAllById(String[] ArrayDATA_IDS)throws Exception{
		return (List<PageData>)dao.findForList("SoftVerMapper.getAllById", ArrayDATA_IDS);
	}
	
	/*
	* 删除文件
	*/
	public void delTp(PageData pd)throws Exception{
		dao.update("SoftVerMapper.delTp", pd);
	}
	/*
	 * 更新
	 * */
	public void updatepm(PageData pd)throws Exception{
		dao.save("SoftVerMapper.updatepm", pd);
	}
}

