package cn.appsys.service.appversion;


import java.util.List;


import cn.appsys.pojo.AppVersion;

public interface AppVersionService {


	List<AppVersion> getAppVersionById(int id);

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
