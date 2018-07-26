package com.lsy.ritu.service.system.project;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lsy.ritu.dao.DaoSupport;
import com.lsy.ritu.entity.Page;
import com.lsy.ritu.util.PageData;

@Service("projectplanService")
public class ProjectPlanService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("ProjectPlanMapper.insert", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("ProjectPlanMapper.delete", pd);
	}
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ProjectPlanMapper.deleteAll", ArrayDATA_IDS);
	}
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("ProjectPlanMapper.update", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProjectPlanMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProjectPlanMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProjectPlanMapper.findById", pd);
	}
	 
	/*
	* 批量获取
	*/
	public List<PageData> getAllById(String[] ArrayDATA_IDS)throws Exception{
		return (List<PageData>)dao.findForList("ProjectPlanMapper.getAllById", ArrayDATA_IDS);
	}
	
	/*
	 * 更新
	 * */
	public void updatepm(PageData pd)throws Exception{
		dao.save("ProjectPlanMapper.updatepm", pd);
	}
}
