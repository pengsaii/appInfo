package cn.appsys.service.appversion;

import java.util.List;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {

	List<AppVersion> getAppVersionById(int id);

}
