package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.QueryAppInfoVO;

public interface AppInfoMapper {
	/**
	 * 获取总数量
	 * @param queryAppInfoVO
	 * @return
	 */
	int getAppInfoCount(QueryAppInfoVO queryAppInfoVO);

	/**
	 * 分页查询信息列表
	 * @param queryAppInfoVO
	 * @return
	 */
	List<AppInfo> getAppInfoList(QueryAppInfoVO queryAppInfoVO);

	AppInfo getAppInfo(@Param("id")Integer id, @Param("APKName")String APKName);

	/**
	 * 添加app基础信息
	 * @param appInfo
	 * @return
	 */
	int add(AppInfo appInfo);

	/**
	 * 删除logo图片
	 * @param id
	 * @return
	 */
	int deleAppLogo(@Param("id")Integer id);

	/*
	 * 修改信息
	 */
	int modify(AppInfo appInfo);

}
