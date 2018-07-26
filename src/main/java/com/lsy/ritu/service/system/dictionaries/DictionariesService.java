package com.lsy.ritu.service.system.dictionaries;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lsy.ritu.dao.DaoSupport;
import com.lsy.ritu.entity.Page;
import com.lsy.ritu.entity.system.Dictionaries;
import com.lsy.ritu.entity.system.Role;
import com.lsy.ritu.util.PageData;

@Service("dictionariesService")
public class DictionariesService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	//新增
	public void save(PageData pd)throws Exception{
		dao.save("DictionariesMapper.save", pd);
	}
	
	//修改
	public void edit(PageData pd)throws Exception{
		dao.update("DictionariesMapper.edit", pd);
	}
	
	//通过id获取数据
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DictionariesMapper.findById", pd);
	}
	
	public PageData findByName(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DictionariesMapper.findByNAME", pd);
	}
	
	//查询总数
	public PageData findCount(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DictionariesMapper.findCount", pd);
	}
	
	//查询某编码
	public PageData findBmCount(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DictionariesMapper.findBmCount", pd);
	}
	
	//列出同一父类id下的数据
	public List<PageData> dictlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("DictionariesMapper.dictlistPage", page);
		
	}
	
	//删除
	public void delete(PageData pd) throws Exception {
		dao.delete("DictionariesMapper.delete", pd);
		
	}
	public List<Dictionaries> listAllDictionaries() throws Exception {
		return (List<Dictionaries>) dao.findForList("DictionariesMapper.listAllDictionaries", null);
		
	}
	public List<Dictionaries> listGetDictionaries(String PARENT_ID) throws Exception { 
		return (List<Dictionaries>) dao.findForList("DictionariesMapper.listGetDictionaries", PARENT_ID);
		
	}
	public List<PageData> listAll() throws Exception {
		return (List<PageData>) dao.findForList("DictionariesMapper.listall", null);
		
	}
	
}
