package cn.appsys.service.appversion;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;

@Service
public class AppVersionServiceImpl implements AppVersionService {

	@Resource
	private AppVersionMapper appVersionMapper;
	
	@Override
	public List<AppVersion> getAppVersionById(int id) {
		return appVersionMapper.getAppVersionById(id);
	}

	public AppVersion getAppVersionById(Integer id) {
		return appVersionMapper.getAppVersionById(id);
	}

	@Override
	public boolean deleteApkFile(Integer id) {
		boolean flag = false;
		if(appVersionMapper.deleteApkFile(id) > 0) {
			flag = true;
		}
		return flag;
	}
	
}
