package cn.appsys.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper {

	List<AppVersion> getAppVersionById(int id);

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
