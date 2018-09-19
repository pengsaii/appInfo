package cn.appsys.service.appinfo;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.QueryAppInfoVO;
import cn.appsys.util.PageBean;

public interface AppInfoService {

	/**
	 * 根据条件进行分页查询
	 * @param pageBean
	 * @param queryAppInfoVO
	 */
	void getAppInfoList(PageBean<AppInfo> pageBean, QueryAppInfoVO queryAppInfoVO);

	AppInfo getAppInfo(Integer id, String APKName);

	/**
	 * 添加
	 * @param appInfo
	 * @return
	 */
	boolean add(AppInfo appInfo);

}
