package cn.appsys.service.appversion;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {

	/**
	 * 根据id获取AppVersion
	 * @param id
	 * @return
	 */
	AppVersion getAppVersionById(Integer id);

	/**
	 * 删除apk
	 * @param parseInt
	 * @return
	 */
	boolean deleteApkFile(Integer id);

}
