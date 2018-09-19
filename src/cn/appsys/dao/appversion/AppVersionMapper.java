package cn.appsys.dao.appversion;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper {

	/**
	 * 根据id获取AppVersion
	 * @param id
	 * @return
	 */
	AppVersion getAppVersionById(@Param("id")Integer id);

	/**
	 * 删除apk
	 * @param id
	 * @return
	 */
	int deleteApkFile(@Param("id")Integer id);

}
