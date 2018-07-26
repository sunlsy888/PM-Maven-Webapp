package com.lsy.ritu.service.system.project;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lsy.ritu.dao.DaoSupport;
import com.lsy.ritu.entity.Page;
import com.lsy.ritu.util.PageData;


@Service("naviprojectService")
public class NaviProjectService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("NaviProjectMapper.insert", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("NaviProjectMapper.deleteforId", pd);
	}
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("NaviProjectMapper.deleteAll", ArrayDATA_IDS);
	}
	/*
	* 批量更新
	*/
	public void updateState(PageData pd)throws Exception{
		dao.update("NaviProjectMapper.updateState", pd);
	}
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("NaviProjectMapper.updateById", pd);
	}
	
	/*
	*列表模糊查询
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("NaviProjectMapper.datalistPage", page);
	}
	/*
	*列表模糊查询参与
	*/
	public List<PageData> listcy(Page page)throws Exception{
		return (List<PageData>)dao.findForList("NaviProjectMapper.datalistPagecy", page);
	}
	/*
	*列表精确查找
	*/
	public List<PageData> getlist(Page page)throws Exception{
		return (List<PageData>)dao.findForList("NaviProjectMapper.getlistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NaviProjectMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("NaviProjectMapper.findById", pd);
	}
	/*
	* 通过PId获取数据
	*/
	public PageData findByPId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("NaviProjectMapper.findByPId", pd);
	}
	/*
	* 通过PName获取数据
	*/
	public PageData findByPN(PageData pd)throws Exception{
		return (PageData)dao.findForObject("NaviProjectMapper.findByPN", pd);
	}
	/*
	* 批量获取
	*/
	public List<PageData> getAllById(String[] ArrayDATA_IDS)throws Exception{
		return (List<PageData>)dao.findForList("NaviProjectMapper.getAllById", ArrayDATA_IDS);
	}
	
	/*
	 * 更新
	 * */
	public void update(PageData pd)throws Exception{
		dao.save("NaviProjectMapper.updateById", pd);
	}
}
