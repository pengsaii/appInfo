package cn.appsys.dao.appversion;

import java.util.List;

import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper {

	List<AppVersion> getAppVersionById(int id);

}
