package com.lsy.ritu.service.system.require;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lsy.ritu.dao.DaoSupport;
import com.lsy.ritu.entity.Page;
import com.lsy.ritu.util.PageData;

@Service("requireService")
public class RequireService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("RequireMapper.insert", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("RequireMapper.delete", pd);
	}
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("RequireMapper.deleteAll", ArrayDATA_IDS);
	}
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("RequireMapper.update", pd);
	}
	/*
	* 修改
	*/
	public void updateState(PageData pd)throws Exception{
		dao.update("RequireMapper.updateState", pd);
	}
	
	/*
	* 修改
	*/
	public void updatepm(PageData pd)throws Exception{
		dao.update("RequireMapper.updatepm", pd);
	}
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RequireMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RequireMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RequireMapper.findById", pd);
	}
	 
	/*
	* 批量获取
	*/
	public List<PageData> getAllById(String[] ArrayDATA_IDS)throws Exception{
		return (List<PageData>)dao.findForList("RequireMapper.getAllById", ArrayDATA_IDS);
	}
}
