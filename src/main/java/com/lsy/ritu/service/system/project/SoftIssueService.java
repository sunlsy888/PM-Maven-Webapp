package com.lsy.ritu.service.system.project;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lsy.ritu.dao.DaoSupport;
import com.lsy.ritu.entity.Page;
import com.lsy.ritu.util.PageData;


@Service("softissueService")
public class SoftIssueService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("SoftIssueMapper.insert", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("SoftIssueMapper.deleteforId", pd);
	}
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SoftIssueMapper.deleteAll", ArrayDATA_IDS);
	}
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("SoftIssueMapper.updateById", pd);
	}
	
	/*
	*列表模糊查询
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SoftIssueMapper.datalistPage", page);
	}
	/*
	*列表精确查找
	*/
	public List<PageData> getlist(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SoftIssueMapper.getlistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SoftIssueMapper.listAll", pd);
	}
	/*
	*列表(全部)
	*/
	public List<PageData> listAll2(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SoftIssueMapper.listAll", page);
	}
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SoftIssueMapper.findById", pd);
	}
 
	/*
	* 批量获取
	*/
	public List<PageData> getAllById(String[] ArrayDATA_IDS)throws Exception{
		return (List<PageData>)dao.findForList("SoftIssueMapper.getAllById", ArrayDATA_IDS);
	}
	
	/*
	 * 更新
	 * */
	public void update(PageData pd)throws Exception{
		dao.save("SoftIssueMapper.updateById", pd);
	}
	/*
	 * 更新
	 * */
	public void updateState(PageData pd)throws Exception{
		dao.save("SoftIssueMapper.updateState", pd);
	}
	
	/*
	 * 更新
	 * */
	public void updatepm(PageData pd)throws Exception{
		dao.save("SoftIssueMapper.updatepm", pd);
	}
}
