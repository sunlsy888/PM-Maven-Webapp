package com.lsy.ritu.service.system.project;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lsy.ritu.dao.DaoSupport;
import com.lsy.ritu.entity.Page;
import com.lsy.ritu.util.PageData;


@Service("tongjiService")
public class TongJiService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 根据状态统计 findPStateForNaviproject
	*/
	public List<PageData> findPStateForNaviproject(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TongjiMapper.findPStateForNaviproject", page);
	}
	 
	
	/*
	* 按日 同一日期 findDateForNaviproject
	*/
	public List<PageData> findDateForNaviproject(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TongjiMapper.findDateForNaviproject", page);
	}
	/*
	* 按周 findWeekForNaviproject
	*/
	public List<PageData> findWeekForNaviproject(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TongjiMapper.findWeekForNaviproject", page);
	}
	/*
	* 周一到周五每天的统计结果 findDayNameForNaviproject
	*/
	public List<PageData> findDayNameForNaviproject(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TongjiMapper.findDayNameForNaviproject", page);
	}
	
	/*
	*统计本周数据 findCurWeekForNaviproject
	*/
	public List<PageData> findCurWeekForNaviproject(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TongjiMapper.findCurWeekForNaviproject", page);
	}
	/*
	*按月统计 findMonthForNaviproject
	*/
	public List<PageData> findMonthForNaviproject(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TongjiMapper.findMonthForNaviproject", page);
	}
	
	/*
	*统计本月数据 findCurMonthForNaviproject
	*/
	public List<PageData> findCurMonthForNaviproject(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TongjiMapper.findCurMonthForNaviproject", page);
	}
	/*
	*按季统计 findQuarterForNaviproject
	*/
	public List<PageData> findQuarterForNaviproject(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TongjiMapper.findQuarterForNaviproject", page);
	}
	/*
	*  按年统计 findYearForNaviproject
	*/
	public List<PageData> findYearForNaviproject(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TongjiMapper.findYearForNaviproject", page);
	}
 
	 
	
	
	/*
	* 根据状态统计 findStateForRequire
	*/
	public List<PageData> findStateForRequire(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TongjiMapper.findStateForRequire", page);
	}
	
	/*
	* 根据状态统计 findStateForSoftIssue
	*/
	public List<PageData> findStateForSoftIssue(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TongjiMapper.findStateForSoftIssue", page);
	}
}
