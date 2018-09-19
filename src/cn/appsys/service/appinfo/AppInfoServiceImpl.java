package cn.appsys.service.appinfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.QueryAppInfoVO;
import cn.appsys.util.PageBean;

@Service
public class AppInfoServiceImpl implements AppInfoService {
	@Resource
	private AppInfoMapper appInfoMapper;
	
	@Override
	public AppInfo getAppInfo(Integer id, String APKName) {
		return appInfoMapper.getAppInfo(id,APKName);
	}

	@Override
	public void getAppInfoList(PageBean<AppInfo> pageBean, QueryAppInfoVO queryAppInfoVO) {
		int totalCount = appInfoMapper.getAppInfoCount(queryAppInfoVO);
		pageBean.setTotalCount(totalCount);
		
		queryAppInfoVO.setStartIndex(pageBean.getStartIndex());
		queryAppInfoVO.setPageSize(pageBean.getPageSize());
		List<AppInfo> result = appInfoMapper.getAppInfoList(queryAppInfoVO);
		pageBean.setResult(result);
	}

	@Override
	public AppInfo getAppInfoById(int id) {
		
		return appInfoMapper.getAppInfoById(id);
	}

	public boolean add(AppInfo appInfo) {
		boolean flag = false;
		if(appInfoMapper.add(appInfo)>0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean deleteAppLogo(Integer id) {
		boolean flag = false;
		if(appInfoMapper.deleAppLogo(id) > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean modify(AppInfo appInfo) {
		boolean flag = false;
		if(appInfoMapper.modify(appInfo) > 0) {
			flag = true;
		}
		return flag;
	}

}
